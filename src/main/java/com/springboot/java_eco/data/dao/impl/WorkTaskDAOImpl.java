package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.querydsl.core.Tuple;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.WorkTaskDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.workTask.WorkTaskDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.bom.BomRepository;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.factory.FactoryRepository;
import com.springboot.java_eco.data.repository.factorySub.FactorySubRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.stock.StockRepository;
import com.springboot.java_eco.data.repository.stockApproval.StockApprovalRepository;
import com.springboot.java_eco.data.repository.stockRecord.StockRecordRepository;
import com.springboot.java_eco.data.repository.stockRequest.StockRequestRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import com.springboot.java_eco.data.repository.workPlan.WorkPlanRepository;
import com.springboot.java_eco.data.repository.workTask.WorkTaskRepository;
import com.springboot.java_eco.data.repository.workTaskPacking.WorkTaskPackingRepository;
import com.springboot.java_eco.data.repository.workTaskProduct.WorkTaskProductRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class WorkTaskDAOImpl implements WorkTaskDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(WorkTaskDAOImpl.class);

    private final WorkTaskRepository workTaskRepository;
    private final WorkPlanRepository workPlanRepository;
    private final StockRequestRepository stockRequestRepository;

    private final StockRepository stockRepository;
    private final StockRecordRepository stockRecordRepository;

    private final UserRepository userRepository;

    private final BomRepository bomRepository;

    private final CompanyRepository companyRepository;

    private final ItemRepository itemRepository;

    private final StockApprovalRepository stockApprovalRepository;

    private final FactoryRepository factoryRepository;

    private final FactorySubRepository factorySubRepository;

    private final WorkTaskProductRepository workTaskProductRepository;
    private final WorkTaskPackingRepository workTaskPackingRepository;


    @Autowired
    public WorkTaskDAOImpl(WorkTaskRepository workTaskRepository,
                           WorkPlanRepository workPlanRepository,
                           StockRequestRepository stockRequestRepository,
                           BomRepository bomRepository,
                           CompanyRepository companyRepository,
                           UserRepository userRepository,
                           ItemRepository itemRepository,
                           StockApprovalRepository stockApprovalRepository,
                           StockRepository stockRepository,
                           StockRecordRepository stockRecordRepository,
                           FactoryRepository factoryRepository,
                           FactorySubRepository factorySubRepository,
                           WorkTaskProductRepository workTaskProductRepository,
                           WorkTaskPackingRepository workTaskPackingRepository

                          ) {
        this.workTaskRepository = workTaskRepository;
        this.workPlanRepository = workPlanRepository;
        this.userRepository = userRepository;
        this.stockRequestRepository = stockRequestRepository;
        this.bomRepository = bomRepository;
        this.companyRepository = companyRepository;
        this.itemRepository = itemRepository;
        this.stockApprovalRepository = stockApprovalRepository;
        this.stockRepository = stockRepository;
        this.stockRecordRepository = stockRecordRepository;
        this.factoryRepository = factoryRepository;
        this.factorySubRepository = factorySubRepository;
        this.workTaskProductRepository = workTaskProductRepository;
        this.workTaskPackingRepository = workTaskPackingRepository;
    }

    @Override
    public CommonResultDto insertWorkTask(WorkTaskDto workTaskDto) throws Exception {
        WorkPlan workPlan = workPlanRepository.findByUid(workTaskDto.getWork_plan_uid());
        Company company = companyRepository.findByUid(workTaskDto.getCompany_uid());
        Bom bom = bomRepository.findByUid(workTaskDto.getBom_uid());
        User user = userRepository.getById(workTaskDto.getUser_id());

        WorkTask workTask = new WorkTask();

        workTask.setBom(bom);
        workTask.setCompany(company);
        workTask.setWorkPlan(workPlan);
        workTask.setUser(user);

        workTask.setTask_qty(workTaskDto.getTask_qty());
        workTask.setSuccess_qty(0D);
        workTask.setFail_qty(0D);
        workTask.setWork_start_date(workTaskDto.getWork_start_date());
        workTask.setWork_end_date(workTaskDto.getWork_start_date());
        workTask.setMaterial_order(1L);
        workTask.setMeasure_order(0L);
        workTask.setProduction_order(0L);
        workTask.setPacking_order(0L);




        workTask.setUnit(workTaskDto.getUnit());
        workTask.setStatus("준비");

        workTask.setCreated(LocalDateTime.now());

        WorkTask insertWorkTask = workTaskRepository.save(workTask);

        Long uid = insertWorkTask.getUid();
        CommonResultDto CommonResultDto = new CommonResultDto();

        if(uid != null){
            // 제조지시에 맞는 BOM 자재를 검색해서 save 해야함
            List<Bom> selectedBom = bomRepository.findByMain(bom.getMain());

            if(selectedBom != null && !selectedBom.isEmpty()){
                for(Bom insertBom :selectedBom){
                    StockRequest stockRequest = new StockRequest();
                    stockRequest.setItem(insertBom.getItem());
                    stockRequest.setCompany(company);
                    stockRequest.setUser(user);
                    stockRequest.setWorkTask(workTask);
                    stockRequest.setReq_qty(workTask.getTask_qty() * insertBom.getQty());
                    stockRequest.setUnit(insertBom.getItem().getInout_unit());

                    stockRequest.setStatus("요청");
                    stockRequest.setCreated(LocalDateTime.now());
                    StockRequest insertStockRequest = stockRequestRepository.save(stockRequest);
                    Long stockRequestUid = insertStockRequest.getUid();
                    if(stockRequestUid == null) {
                        setFailResult(CommonResultDto);
                        return CommonResultDto;
                    }
                }

            }

            setSuccessResult(CommonResultDto);
            return CommonResultDto;


            }else{
                setFailResult(CommonResultDto);
                return CommonResultDto;
            }
    }

    @Override
    public CommonResultDto updateWorkTask(WorkTaskDto workTaskDto) throws Exception {

        Company company = companyRepository.findByUid(workTaskDto.getCompany_uid());
        Bom bom = bomRepository.findByUid(workTaskDto.getBom_uid());
        WorkPlan workPlan = workPlanRepository.findByUid(workTaskDto.getWork_plan_uid());


        Optional<WorkTask> selectedWorkTask = workTaskRepository.findById(workTaskDto.getUid());

        WorkTask updatedWorkTask;
        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedWorkTask.isPresent()) {
            WorkTask workTask = selectedWorkTask.get();
            workTask.setBom(bom);

            workTask.setCompany(company);
            workTask.setWorkPlan(workPlan);

            workTask.setTask_qty(workTaskDto.getTask_qty());
            workTask.setSuccess_qty(workTaskDto.getSuccess_qty());
            workTask.setFail_qty(workTaskDto.getFail_qty());
            workTask.setWork_start_date(workTaskDto.getWork_start_date());
            workTask.setWork_end_date(workTaskDto.getWork_start_date());
            workTask.setMaterial_order(workTaskDto.getMaterial_order());
            workTask.setUnit(workTaskDto.getUnit());
            workTask.setStatus("준비");

            workTask.setCreated(LocalDateTime.now());

            workTask.setUpdated(LocalDateTime.now());
            updatedWorkTask = workTaskRepository.save(workTask);

            Long uid = updatedWorkTask.getUid();

            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        } else {

            setFailResult(CommonResultDto);
            return CommonResultDto;
        }

    }

    @Override
    public CommonResultDto approvalWorkTask(WorkTaskDto workTaskDto) throws Exception {

        Company company = companyRepository.findByUid(workTaskDto.getCompany_uid());
        User user = userRepository.getById(workTaskDto.getUser_id());

        Optional<WorkTask> selectedWorkTask = workTaskRepository.findById(workTaskDto.getUid());

        WorkTask updatedWorkTask;


        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedWorkTask.isPresent()) {
            WorkTask workTask = selectedWorkTask.get();
            workTask.setBom(workTask.getBom());

            workTask.setCompany(company);
            workTask.setWorkPlan(workTask.getWorkPlan());

            workTask.setTask_qty(workTask.getTask_qty());
            workTask.setSuccess_qty((double) 0);
            workTask.setFail_qty((double) 0);
            workTask.setWork_start_date(workTask.getWork_start_date());
            workTask.setWork_end_date(workTask.getWork_end_date());
            workTask.setMaterial_order(workTaskDto.getMaterial_order());
            workTask.setMeasure_order(1L);
            workTask.setUnit(workTask.getUnit());
            workTask.setStatus("계량지시");

            workTask.setCreated(workTask.getCreated());

            workTask.setUpdated(LocalDateTime.now());
            updatedWorkTask = workTaskRepository.save(workTask);

            Long uid = updatedWorkTask.getUid();

            List<Map<String, Object>> stockApprovalList = workTaskDto.getStock_approval();

            if (stockApprovalList != null) {
                for (Map<String, Object> stockApprovalData : stockApprovalList) {
                    StockApproval stockApproval = new StockApproval();
                    stockApproval.setWorkTask(updatedWorkTask);

                    if(stockApprovalData.containsKey("item_uid")){
                        Long itemUid = Long.parseLong(stockApprovalData.get("item_uid").toString());
                        Item selectedItem = itemRepository.findByUid(itemUid);
                        stockApproval.setItem(selectedItem);

                    }
                    stockApproval.setCompany(company);
                    stockApproval.setUser(user);
                    if (stockApprovalData.get("lot") != null && !stockApprovalData.get("lot").toString().isEmpty()) {
                        stockApproval.setLot(stockApprovalData.get("lot").toString());
                    } else {
                        setFailResult(CommonResultDto);
                        return CommonResultDto;
                    }

                    if (stockApprovalData.get("unit") != null && !stockApprovalData.get("unit").toString().isEmpty()) {
                        stockApproval.setUnit(stockApprovalData.get("unit").toString());
                    } else {
                        stockApproval.setUnit("");
                    }
                    if (!stockApprovalData.get("prev_qty").toString().isEmpty()) {
                        try {
                            stockApproval.setPrev_qty(Double.valueOf(stockApprovalData.get("prev_qty").toString()));
                        } catch (NumberFormatException e) {
                            stockApproval.setPrev_qty((double) 0L);
                        }
                    }
                    if (!stockApprovalData.get("out_qty").toString().isEmpty()) {
                        try {
                            stockApproval.setOut_qty(Double.valueOf(stockApprovalData.get("out_qty").toString()));
                        } catch (NumberFormatException e) {
                            stockApproval.setOut_qty((double) 0L);
                        }
                    }
                    stockApproval.setMeasure_qty(0D);
                    stockApproval.setStatus("승인");
                    stockApproval.setCreated(LocalDateTime.now());
                    StockApproval insertStockApproval = stockApprovalRepository.save(stockApproval);


                    Long stockApprovalUid = insertStockApproval.getUid();
                    if(stockApprovalUid != null){

                        if(stockApprovalData.containsKey("factory_uid")){
                            Long factoryUid = Long.parseLong(stockApprovalData.get("factory_uid").toString());
                            Factory selectedFactory = factoryRepository.findByUid(factoryUid);
                            if(stockApprovalData.containsKey("factory_sub_uid")){
                                Long factorySubUid = Long.parseLong(stockApprovalData.get("factory_sub_uid").toString());
                                FactorySub selectedFactorySub = factorySubRepository.findByUid(factorySubUid);


                                Optional<Stock> selectedStock = Optional.ofNullable(stockRepository.findByLotAndItemAndCompanyAndFactoryAndFactorySub(stockApproval.getLot(), stockApproval.getItem(), company, selectedFactory, selectedFactorySub));

                                if(selectedStock.isPresent()){
                                    Stock stock = selectedStock.get();
                                    Double updateQty = stock.getQty() -  stockApproval.getOut_qty();
                                    stock.setQty(updateQty);
                                    Stock updateStock = stockRepository.save(stock);
                                    Long stockUid = updateStock.getUid();
                                    if(stockUid != null) {

                                        StockRecord stockRecord = new StockRecord();
                                        stockRecord.setItem(stockApproval.getItem());
                                        stockRecord.setCompany(company);

                                        stockRecord.setOutFactory(selectedFactory);
                                        stockRecord.setOutFactorySub(selectedFactorySub);

                                        stockRecord.setInFactory(selectedFactory);
                                        stockRecord.setInFactorySub(selectedFactorySub);

                                        stockRecord.setLot(stockApproval.getLot());
                                        stockRecord.setQty(stockApproval.getOut_qty());

                                        stockRecord.setUnit(stockApproval.getUnit());
                                        stockRecord.setType("출고");
                                        stockRecord.setStatus("가용");
                                        stockRecord.setReason("생산용 불출");
                                        stockRecord.setCreated(LocalDateTime.now());

                                        StockRecord insertStockRecord = stockRecordRepository.save(stockRecord);
                                        Long stockRecordUid = insertStockRecord.getUid();

                                        if(stockRecordUid == null) {
                                            setFailResult(CommonResultDto);
                                            return CommonResultDto;

                                        }


                                    }

                                }else{
                                    setFailResult(CommonResultDto);
                                    return CommonResultDto;
                                }

                            }
                        }



                    }


                }

            }else{
                setFailResult(CommonResultDto);
                return CommonResultDto;
            }

        } else {

            setFailResult(CommonResultDto);
            return CommonResultDto;
        }
        setSuccessResult(CommonResultDto);
        return CommonResultDto;

    }

    @Override
    public CommonResultDto measureWorkTask(WorkTaskDto workTaskDto) throws Exception {

        Company company = companyRepository.findByUid(workTaskDto.getCompany_uid());
        User user = userRepository.getById(workTaskDto.getUser_id());

        Optional<WorkTask> selectedWorkTask = workTaskRepository.findById(workTaskDto.getUid());

        WorkTask updatedWorkTask;


        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedWorkTask.isPresent()) {
            WorkTask workTask = selectedWorkTask.get();
            workTask.setBom(workTask.getBom());

            workTask.setCompany(company);
            workTask.setWorkPlan(workTask.getWorkPlan());

            workTask.setTask_qty(workTask.getTask_qty());
            workTask.setSuccess_qty((double) 0);
            workTask.setFail_qty((double) 0);
            workTask.setWork_start_date(workTask.getWork_start_date());
            workTask.setWork_end_date(workTask.getWork_end_date());
            workTask.setMeasure_order(workTaskDto.getMeasure_order());
            workTask.setProduction_order(1L);
            workTask.setUnit(workTask.getUnit());
            workTask.setStatus("제조지시");
            workTask.setCreated(workTask.getCreated());
            workTask.setUpdated(LocalDateTime.now());
            updatedWorkTask = workTaskRepository.save(workTask);

            Long uid = updatedWorkTask.getUid();

            List<Map<String, Object>> stockApprovalList = workTaskDto.getStock_approval();

            if (stockApprovalList != null) {
                for (Map<String, Object> stockApprovalData : stockApprovalList) {
                    if(stockApprovalData.containsKey("uid")){
                        Long Uid = Long.parseLong(stockApprovalData.get("uid").toString());

                        Optional<StockApproval> selectedStockApproval = Optional.ofNullable(stockApprovalRepository.findByUid(Uid));

                        if(selectedStockApproval.isPresent()) {
                            StockApproval stockApproval = selectedStockApproval.get();

                            if (!stockApprovalData.get("measure_qty").toString().isEmpty()) {
                                try {
                                    stockApproval.setMeasure_qty(Double.valueOf(stockApprovalData.get("measure_qty").toString()));
                                } catch (NumberFormatException e) {
                                    stockApproval.setMeasure_qty((double) 0L);
                                }
                            }
                            stockApproval.setStatus("계량완료");
                            stockApprovalRepository.save(stockApproval);

                        }else{
                            setFailResult(CommonResultDto);
                            return CommonResultDto;
                        }

                    }
                }
                setSuccessResult(CommonResultDto);
                return CommonResultDto;

            }else{
                setFailResult(CommonResultDto);
                return CommonResultDto;
            }

        } else {

            setFailResult(CommonResultDto);
            return CommonResultDto;
        }


    }

    @Override
    public CommonResultDto productionWorkTask(WorkTaskDto workTaskDto) throws Exception {

        Company company = companyRepository.findByUid(workTaskDto.getCompany_uid());
        User user = userRepository.getById(workTaskDto.getUser_id());

        Optional<WorkTask> selectedWorkTask = workTaskRepository.findById(workTaskDto.getUid());

        WorkTask updatedWorkTask;


        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedWorkTask.isPresent()) {
            WorkTask workTask = selectedWorkTask.get();
            workTask.setBom(workTask.getBom());

            workTask.setCompany(company);
            workTask.setWorkPlan(workTask.getWorkPlan());

            workTask.setTask_qty(workTask.getTask_qty());
            workTask.setSuccess_qty((double) 0);
            workTask.setFail_qty((double) 0);
            workTask.setWork_start_date(workTask.getWork_start_date());
            workTask.setWork_end_date(workTask.getWork_end_date());
            workTask.setMeasure_order(workTask.getMeasure_order());
            workTask.setProduction_order(workTaskDto.getProduction_order());
            workTask.setPacking_order(1L);


            workTask.setUnit(workTask.getUnit());
            workTask.setStatus("포장지시");
            workTask.setCreated(workTask.getCreated());
            workTask.setUpdated(LocalDateTime.now());
            updatedWorkTask = workTaskRepository.save(workTask);

            Long uid = updatedWorkTask.getUid();

            List<Map<String, Object>> workTaskProductList = workTaskDto.getWork_task_product();

            if (workTaskProductList != null) {
                for (Map<String, Object> workTaskProductData : workTaskProductList) {
                        WorkTaskProduct workTaskProduct = new WorkTaskProduct();

                        workTaskProduct.setWorkTask(updatedWorkTask);
                        if(workTaskProductData.containsKey("bom_uid")){
                            Long bomUid = Long.parseLong(workTaskProductData.get("bom_uid").toString());
                            Bom selectedBom = bomRepository.findByUid(bomUid);
                            workTaskProduct.setBom(selectedBom);
                        }


                        if (workTaskProductData.get("lot") != null && !workTaskProductData.get("lot").toString().isEmpty()) {
                            workTaskProduct.setLot(workTaskProductData.get("lot").toString());
                        } else {
                            setFailResult(CommonResultDto);
                            return CommonResultDto;
                        }
                    if (workTaskProductData.get("unit") != null && !workTaskProductData.get("unit").toString().isEmpty()) {
                        workTaskProduct.setUnit(workTaskProductData.get("unit").toString());
                    } else {
                        setFailResult(CommonResultDto);
                        return CommonResultDto;
                    }
                    if (workTaskProductData.get("status") != null && !workTaskProductData.get("status").toString().isEmpty()) {
                        workTaskProduct.setStatus(workTaskProductData.get("status").toString());
                    } else {
                        setFailResult(CommonResultDto);
                        return CommonResultDto;
                    }
                    if (!workTaskProductData.get("product_qty").toString().isEmpty()) {
                        try {
                            workTaskProduct.setProduct_qty(Double.valueOf(workTaskProductData.get("product_qty").toString()));
                        } catch (NumberFormatException e) {
                            workTaskProduct.setProduct_qty((double) 0L);
                        }
                    }

                     workTaskProductRepository.save(workTaskProduct);

                }
                setSuccessResult(CommonResultDto);
                return CommonResultDto;

            }else{
                setFailResult(CommonResultDto);
                return CommonResultDto;
            }

        } else {

            setFailResult(CommonResultDto);
            return CommonResultDto;
        }


    }

    @Override
    public CommonResultDto packingWorkTask(WorkTaskDto workTaskDto) throws Exception {

        Company company = companyRepository.findByUid(workTaskDto.getCompany_uid());
        User user = userRepository.getById(workTaskDto.getUser_id());

        Optional<WorkTask> selectedWorkTask = workTaskRepository.findById(workTaskDto.getUid());

        WorkTask updatedWorkTask;


        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedWorkTask.isPresent()) {
            WorkTask workTask = selectedWorkTask.get();
            workTask.setBom(workTask.getBom());

            workTask.setCompany(company);
            workTask.setWorkPlan(workTask.getWorkPlan());

            workTask.setTask_qty(workTask.getTask_qty());
            workTask.setSuccess_qty(workTaskDto.getSuccess_qty());
            workTask.setFail_qty(workTaskDto.getFail_qty());

            workTask.setWork_start_date(workTask.getWork_start_date());
            workTask.setWork_end_date(workTask.getWork_end_date());
            workTask.setPacking_order(2L);
            workTask.setUnit(workTask.getUnit());
            workTask.setStatus("생산완료");
            workTask.setCreated(workTask.getCreated());
            workTask.setUpdated(LocalDateTime.now());
            updatedWorkTask = workTaskRepository.save(workTask);

            Long uid = updatedWorkTask.getUid();

            List<Map<String, Object>> workTaskPackingList = workTaskDto.getWork_task_packing();

            if (workTaskPackingList != null) {
                for (Map<String, Object> workTaskPackingData : workTaskPackingList) {
                    WorkTaskPacking workTaskPacking = new WorkTaskPacking();
                    workTaskPacking.setWorkTask(updatedWorkTask);
                    if(workTaskPackingData.containsKey("work_task_product_uid")){
                        Long workTaskProductUid = Long.parseLong(workTaskPackingData.get("work_task_product_uid").toString());
                        WorkTaskProduct selectedWorkTaskProduct = workTaskProductRepository.findByUid(workTaskProductUid);
                        workTaskPacking.setWorkTaskProduct(selectedWorkTaskProduct);

                    }
                    if(workTaskPackingData.containsKey("bom_uid")){
                        Long bomUid = Long.parseLong(workTaskPackingData.get("bom_uid").toString());
                        Bom selectedBom = bomRepository.findByUid(bomUid);
                        workTaskPacking.setBom(selectedBom);
                    } if(workTaskPackingData.containsKey("factory_uid")){
                        Long factoryUid = Long.parseLong(workTaskPackingData.get("factory_uid").toString());
                        Factory selectedFactory = factoryRepository.findByUid(factoryUid);
                        workTaskPacking.setFactory(selectedFactory);
                    }
                    if(workTaskPackingData.containsKey("factory_sub_uid")){
                        Long factorySubUid = Long.parseLong(workTaskPackingData.get("factory_sub_uid").toString());
                        FactorySub selectedFactorySub = factorySubRepository.findByUid(factorySubUid);
                        workTaskPacking.setFactorySub(selectedFactorySub);
                    }
                    if (workTaskPackingData.get("lot") != null && !workTaskPackingData.get("lot").toString().isEmpty()) {
                        workTaskPacking.setLot(workTaskPackingData.get("lot").toString());

                    } else {
                        setFailResult(CommonResultDto);
                        return CommonResultDto;
                    }
                    if (!workTaskPackingData.get("inbox_qty").toString().isEmpty()) {
                        try {
                            workTaskPacking.setInbox_qty(Integer.valueOf(workTaskPackingData.get("inbox_qty").toString()));
                        } catch (NumberFormatException e) {
                            workTaskPacking.setInbox_qty(0);
                        }
                    }
                    if (!workTaskPackingData.get("box_qty").toString().isEmpty()) {
                        try {
                            workTaskPacking.setBox_qty(Integer.valueOf(workTaskPackingData.get("box_qty").toString()));
                        } catch (NumberFormatException e) {
                            workTaskPacking.setBox_qty(0);
                        }
                    }
                    if (!workTaskPackingData.get("etc_qty").toString().isEmpty()) {
                        try {
                            workTaskPacking.setEtc_qty(Integer.valueOf(workTaskPackingData.get("etc_qty").toString()));
                        } catch (NumberFormatException e) {
                            workTaskPacking.setEtc_qty(0);
                        }
                    }

                    WorkTaskPacking insertWorkTaskPacking = workTaskPackingRepository.save(workTaskPacking);
                    Long workTaskPackingUid = insertWorkTaskPacking.getUid();
                    if(workTaskPackingUid  != null){

                            Stock stock = new Stock();
                        stock.setLot(workTaskPacking.getLot());

                        stock.setItem(workTask.getBom().getItem());
                            stock.setCompany(company);
                            stock.setUser(user);
                            stock.setFactory(workTaskPacking.getFactory());
                            stock.setFactorySub(workTaskPacking.getFactorySub());

                            stock.setQty(Double.valueOf(workTaskPacking.getEtc_qty()));
                            stock.setUnit(workTask.getUnit());
                            stock.setStatus("가용");


                        Stock insertStock = stockRepository.save(stock);
                            Long stockUid = insertStock.getUid();
                            if(stockUid != null) {

                                StockRecord stockRecord = new StockRecord();
                                stockRecord.setItem(workTask.getBom().getItem());
                                stockRecord.setCompany(company);

                                stockRecord.setOutFactory(workTaskPacking.getFactory());
                                stockRecord.setOutFactorySub(workTaskPacking.getFactorySub());

                                stockRecord.setInFactory(workTaskPacking.getFactory());
                                stockRecord.setInFactorySub(workTaskPacking.getFactorySub());

                                stockRecord.setLot(workTaskPacking.getLot());
                                stockRecord.setQty(Double.valueOf(workTaskPacking.getEtc_qty()));

                                stockRecord.setUnit(workTask.getUnit());
                                stockRecord.setType("포장입고");
                                stockRecord.setStatus("가용");
                                stockRecord.setReason("포장입고");
                                stockRecord.setCreated(LocalDateTime.now());

                                StockRecord insertStockRecord = stockRecordRepository.save(stockRecord);
                                Long stockRecordUid = insertStockRecord.getUid();

                                if(stockRecordUid == null) {
                                    setFailResult(CommonResultDto);
                                    return CommonResultDto;

                                }


                            }




                    }




                }
                setSuccessResult(CommonResultDto);
                return CommonResultDto;

            }else{
                setFailResult(CommonResultDto);
                return CommonResultDto;
            }

        } else {

            setFailResult(CommonResultDto);
            return CommonResultDto;
        }


    }



    @Override
    public List<WorkTask> selectWorkTask(CommonInfoSearchDto commonInfoSearchDto) {
        return workTaskRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<WorkTask> selectTotalWorkTask(CommonSearchDto commonSearchDto) {
        return workTaskRepository.findAll(commonSearchDto);

    }

    @Override
    public String deleteWorkTask(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<WorkTask> selectedWorkTask = Optional.ofNullable(workTaskRepository.findByUid(uid));
            if (selectedWorkTask.isPresent()) {
                WorkTask workTask = selectedWorkTask.get();

                workTaskRepository.delete(workTask);
            } else {
                throw new Exception("WORKPLAN with UID " + uid + " not found.");
            }
        }
        return "WORKPLAN deleted successfully";
    }


    private void setSuccessResult(CommonResultDto result){
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
    private void setFailResult(CommonResultDto result){
        result.setSuccess(false);
        result.setCode(CommonResponse.FAIL.getCode());
        result.setMsg(CommonResponse.FAIL.getMsg());
    }

}