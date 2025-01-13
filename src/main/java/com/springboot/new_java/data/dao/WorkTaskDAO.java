package com.springboot.new_java.data.dao;

import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.workTask.WorkTaskDto;
import com.springboot.new_java.data.entity.WorkTask;

import java.util.List;


public interface WorkTaskDAO {


    List<WorkTask> selectWorkTask(CommonInfoSearchDto commonInfoSearchDto);
    List<WorkTask> selectTotalWorkTask(CommonSearchDto commonSearchDto);

     CommonResultDto insertWorkTask(WorkTaskDto workTaskDto)  throws Exception; // 최초작업지시
    CommonResultDto updateWorkTask(WorkTaskDto workTaskDto)  throws Exception; // 자재출고요청시에만 가능

    CommonResultDto approvalWorkTask(WorkTaskDto workTaskDto)  throws Exception; // 자재출고승인 -> 계량지시까지 함
    CommonResultDto measureWorkTask(WorkTaskDto workTaskDto)  throws Exception; // 계량완료 -> 제조지시까지 함

    CommonResultDto productionWorkTask(WorkTaskDto workTaskDto)  throws Exception; // 제조완료 -> 포장지시까지 함

    CommonResultDto packingWorkTask(WorkTaskDto workTaskDto)  throws Exception; // 포장완료 -> 생산끝

    String deleteWorkTask(List<Long> uid) throws Exception;

}
