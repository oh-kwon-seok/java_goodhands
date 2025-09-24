package com.springboot.new_java.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.new_java.data.dto.care.CareScheduleProtocol;
import com.springboot.new_java.data.dto.care.WeeklySchedule;
import com.springboot.new_java.data.entity.User;
import com.springboot.new_java.data.entity.care.CareSchedule;
import com.springboot.new_java.data.entity.senior.Senior;
import com.springboot.new_java.data.repository.careSchedule.CareScheduleRepository;
import com.springboot.new_java.data.repository.senior.SeniorRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
public class CareScheduleBatchService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Autowired
    private CareScheduleRepository careScheduleRepository;

    @Autowired
    private SeniorRepository seniorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 수동으로 다음주 케어 스케줄 생성
     */

    public Map<String, Object> generateNextWeekScheduleManually() {
        try {
            log.info("다음주 케어 스케줄 배치 작업 시작 - 수동 실행");

            LocalDate nextMonday = getNextMonday();
            LocalDate nextSunday = nextMonday.plusDays(6);

            JobParameters jobParameters = new JobParametersBuilder()
                    .addDate("startTime", new Date())
                    .addString("startDate", nextMonday.toString())
                    .addString("endDate", nextSunday.toString())
                    .addString("jobType", "MANUAL_NEXT_WEEK_GENERATION")
                    .addLong("timestamp", System.currentTimeMillis())
                    .toJobParameters();

            JobExecution jobExecution = jobLauncher.run(createNextWeekScheduleJob(), jobParameters);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("jobStatus", jobExecution.getStatus().toString());
            result.put("startDate", nextMonday.toString());
            result.put("endDate", nextSunday.toString());
            result.put("message", "다음주(" + nextMonday + "~" + nextSunday + ") 케어 스케줄 생성이 완료되었습니다.");

            long createdCount = careScheduleRepository.countByCareReserveDateBetween(nextMonday.toString(), nextSunday.toString());
            result.put("createdScheduleCount", createdCount);

            log.info("다음주 케어 스케줄 배치 작업 완료 - Status: {}, 생성된 스케줄: {}개",
                    jobExecution.getStatus(), createdCount);

            return result;

        } catch (Exception e) {
            log.error("수동 케어 스케줄 생성 실패: {}", e.getMessage(), e);
            throw new RuntimeException("다음주 케어 스케줄 생성에 실패했습니다: " + e.getMessage(), e);
        }
    }

    /**
     * 다음주 케어 스케줄 생성 Job
     */
    public Job createNextWeekScheduleJob() {
        return new JobBuilder("nextWeekCareScheduleJob", jobRepository)
                .start(createNextWeekScheduleStep())
                .build();
    }

    /**
     * 다음주 케어 스케줄 생성 Step
     */
    public Step createNextWeekScheduleStep() {
        return new StepBuilder("nextWeekCareScheduleStep", jobRepository)
                .<Senior, List<CareSchedule>>chunk(10, transactionManager)
                .reader(seniorItemReader())
                .processor(nextWeekScheduleProcessor())
                .writer(careScheduleItemWriter())
                .faultTolerant()
                .skipLimit(50)
                .skip(Exception.class)
                .listener(new NextWeekScheduleStepListener())
                .build();
    }

    /**
     * Senior 데이터 읽기
     */
    public ItemReader<Senior> seniorItemReader() {
        JpaPagingItemReader<Senior> reader = new JpaPagingItemReader<>();

        try {
            reader.setEntityManagerFactory(entityManagerFactory);
            reader.setQueryString("SELECT s FROM Senior s WHERE s.care_schedule_protocol IS NOT NULL");
            reader.setPageSize(10);
            reader.afterPropertiesSet();
        } catch (Exception e) {
            log.error("Senior ItemReader 초기화 실패: {}", e.getMessage(), e);
            throw new RuntimeException("Senior 데이터 읽기 초기화 실패", e);
        }

        return reader;
    }

    /**
     * 다음주 스케줄 생성 프로세서
     */
    public ItemProcessor<Senior, List<CareSchedule>> nextWeekScheduleProcessor() {
        return new ItemProcessor<Senior, List<CareSchedule>>() {
            @Override
            public List<CareSchedule> process(Senior senior) throws Exception {
                try {
                    log.debug("시니어 {} 다음주 스케줄 처리 시작", senior.getUid());

                    // 날짜 계산은 LocalDate를 사용
                    LocalDate nextMonday = getNextMonday();
                    LocalDate nextSunday = nextMonday.plusDays(6);

                    // String 타입의 날짜를 전달
                    List<CareSchedule> schedules = generateSchedulesForDateRange(
                            senior, nextMonday.toString(), nextSunday.toString()
                    );

                    log.debug("시니어 {} 다음주 스케줄 {} 개 생성", senior.getUid(), schedules.size());

                    return schedules.isEmpty() ? null : schedules;

                } catch (Exception e) {
                    log.error("시니어 {} 다음주 스케줄 처리 실패: {}", senior.getUid(), e.getMessage(), e);
                    throw new RuntimeException("시니어 " + senior.getUid() + " 스케줄 생성 실패", e);
                }
            }
        };
    }

    /**
     * ItemWriter: List<CareSchedule>를 DB에 저장
     */
    public ItemWriter<List<CareSchedule>> careScheduleItemWriter() {
        return new ItemWriter<List<CareSchedule>>() {
            @Override
            public void write(Chunk<? extends List<CareSchedule>> chunk) throws Exception {
                List<CareSchedule> allSchedules = new ArrayList<>();
                for (List<CareSchedule> schedules : chunk.getItems()) {
                    allSchedules.addAll(schedules);
                }

                if (!allSchedules.isEmpty()) {
                    careScheduleRepository.saveAll(allSchedules);
                    log.info("케어 스케줄 {} 개 배치 저장 완료", allSchedules.size());
                }
            }
        };
    }

    /**
     * 지정된 날짜 범위의 스케줄 생성
     * 매개변수를 String으로 받음
     */
    private List<CareSchedule> generateSchedulesForDateRange(Senior senior, String startDateStr, String endDateStr) {
        List<CareSchedule> schedules = new ArrayList<>();

        try {
            // String 매개변수를 LocalDate로 변환하여 내부에서 사용
            LocalDate startDate = LocalDate.parse(startDateStr);
            LocalDate endDate = LocalDate.parse(endDateStr);

            CareScheduleProtocol protocol = parseProtocol(senior.getCare_schedule_protocol());
            if (protocol == null) {
                log.warn("시니어 {} 프로토콜 파싱 실패", senior.getUid());
                return schedules;
            }

            LocalDate currentDate = startDate;

            while (!currentDate.isAfter(endDate)) {
                DayOfWeek dayOfWeek = currentDate.getDayOfWeek();
                String dayName = getDayNameInKorean(dayOfWeek);

                WeeklySchedule weeklySchedule = protocol.getWeeklySchedule().get(dayName);

                if (weeklySchedule != null && weeklySchedule.isVisitEnabled() && weeklySchedule.getVisitCount() > 0) {
                    // DB에서 String으로 된 날짜를 조회
                    if (!isDuplicateSchedule(senior.getUid(), currentDate.toString())) {
                        CareSchedule schedule = createCareSchedule(senior, currentDate);
                        schedules.add(schedule);
                        log.debug("시니어 {} 스케줄 생성: {} ({})", senior.getUid(), currentDate, dayName);
                    } else {
                        log.debug("시니어 {} 스케줄 중복으로 건너뜀: {}", senior.getUid(), currentDate);
                    }
                }
                currentDate = currentDate.plusDays(1);
            }
        } catch (Exception e) {
            log.error("시니어 {} 날짜 범위 스케줄 생성 중 오류: {}", senior.getUid(), e.getMessage(), e);
            throw new RuntimeException("스케줄 생성 중 오류", e);
        }
        return schedules;
    }

    /**
     * 다음주 월요일 계산
     */
    private LocalDate getNextMonday() {
        LocalDate today = LocalDate.now();
        int daysUntilNextMonday = DayOfWeek.MONDAY.getValue() - today.getDayOfWeek().getValue();
        if (daysUntilNextMonday <= 0) {
            daysUntilNextMonday += 7;
        }
        return today.plusDays(daysUntilNextMonday);
    }

    /**
     * 요일을 한글로 변환
     */
    private String getDayNameInKorean(DayOfWeek dayOfWeek) {
        switch (dayOfWeek) {
            case MONDAY: return "월";
            case TUESDAY: return "화";
            case WEDNESDAY: return "수";
            case THURSDAY: return "목";
            case FRIDAY: return "금";
            case SATURDAY: return "토";
            case SUNDAY: return "일";
            default: return "";
        }
    }

    /**
     * JSON 프로토콜 파싱
     */
    private CareScheduleProtocol parseProtocol(String protocolJson) {
        try {
            return objectMapper.readValue(protocolJson, CareScheduleProtocol.class);
        } catch (Exception e) {
            log.error("프로토콜 JSON 파싱 실패: {}", e.getMessage());
            return null;
        }
    }

    /**
     * 중복 스케줄 체크
     * 매개변수를 String으로 받음
     */
    private boolean isDuplicateSchedule(Long seniorUid, String reserveDateStr) {
        return careScheduleRepository.existsBySeniorUidAndCareReserveDate(seniorUid, reserveDateStr);
    }

    /**
     * CareSchedule 엔티티 생성
     */
    private CareSchedule createCareSchedule(Senior senior, LocalDate reserveDate) {
        CareSchedule schedule = new CareSchedule();
        schedule.setCareReserveDate(reserveDate.toString());
        User caregiver = userRepository.findById(senior.getCaregiver().getId()).orElse(null);
        User user = userRepository.findById("auto000").orElse(null);

        schedule.setCaregiver(caregiver);
        schedule.setSenior(senior);
        schedule.setUser(user);
        schedule.setCreated(LocalDateTime.now());
        return schedule;
    }

    /**
     * Step 실행 리스너
     */
    public static class NextWeekScheduleStepListener implements StepExecutionListener {
        @Override
        public void beforeStep(StepExecution stepExecution) {
            log.info("다음주 케어 스케줄 생성 Step 시작");
        }

        @Override
        public ExitStatus afterStep(StepExecution stepExecution) {
            Long readCount = stepExecution.getReadCount();
            Long writeCount = stepExecution.getWriteCount();
            Long skipCount = stepExecution.getSkipCount();

            log.info("다음주 케어 스케줄 생성 Step 완료 - 읽기: {}, 쓰기: {}, 스킵: {}",
                    readCount, writeCount, skipCount);

            return stepExecution.getExitStatus();
        }
    }
}