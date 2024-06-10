package com.springboot.java_eco.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.java_eco.common.CommonResponse;
import com.springboot.java_eco.data.dao.ShipOrderDAO;
import com.springboot.java_eco.data.dto.common.CommonInfoSearchDto;
import com.springboot.java_eco.data.dto.common.CommonResultDto;
import com.springboot.java_eco.data.dto.common.CommonSearchDto;
import com.springboot.java_eco.data.dto.shipOrder.ShipOrderDto;
import com.springboot.java_eco.data.entity.*;
import com.springboot.java_eco.data.repository.bom.BomRepository;
import com.springboot.java_eco.data.repository.company.CompanyRepository;
import com.springboot.java_eco.data.repository.factory.FactoryRepository;
import com.springboot.java_eco.data.repository.factorySub.FactorySubRepository;
import com.springboot.java_eco.data.repository.item.ItemRepository;
import com.springboot.java_eco.data.repository.order.OrderRepository;
import com.springboot.java_eco.data.repository.shipOrderSub.ShipOrderSubRepository;
import com.springboot.java_eco.data.repository.stock.StockRepository;
import com.springboot.java_eco.data.repository.stockApproval.StockApprovalRepository;
import com.springboot.java_eco.data.repository.stockRecord.StockRecordRepository;
import com.springboot.java_eco.data.repository.stockRequest.StockRequestRepository;
import com.springboot.java_eco.data.repository.user.UserRepository;
import com.springboot.java_eco.data.repository.workPlan.WorkPlanRepository;
import com.springboot.java_eco.data.repository.shipOrder.ShipOrderRepository;

import com.springboot.java_eco.data.repository.workTaskPacking.WorkTaskPackingRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class ShipOrderDAOImpl implements ShipOrderDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(ShipOrderDAOImpl.class);
    private final OrderRepository orderRepository;

    private final ShipOrderRepository shipOrderRepository;

    private final ShipOrderSubRepository shipOrderSubRepository;

    private final StockRepository stockRepository;
    private final StockRecordRepository stockRecordRepository;

    private final UserRepository userRepository;

    private final BomRepository bomRepository;

    private final CompanyRepository companyRepository;

    private final ItemRepository itemRepository;


    private final FactoryRepository factoryRepository;

    private final FactorySubRepository factorySubRepository;

    private final WorkTaskPackingRepository workTaskPackingRepository;


    @Autowired
    public ShipOrderDAOImpl(ShipOrderRepository shipOrderRepository,
                            ShipOrderSubRepository shipOrderSubRepository,
                            BomRepository bomRepository,
                            CompanyRepository companyRepository,
                            UserRepository userRepository,
                            ItemRepository itemRepository,
                            StockRepository stockRepository,
                            StockRecordRepository stockRecordRepository,
                            FactoryRepository factoryRepository,
                            FactorySubRepository factorySubRepository,
                            OrderRepository orderRepository,
                            WorkTaskPackingRepository workTaskPackingRepository

                          ) {
        this.orderRepository = orderRepository;
        this.shipOrderRepository = shipOrderRepository;
        this.shipOrderSubRepository = shipOrderSubRepository;
        this.userRepository = userRepository;

        this.bomRepository = bomRepository;
        this.companyRepository = companyRepository;
        this.itemRepository = itemRepository;

        this.stockRepository = stockRepository;
        this.stockRecordRepository = stockRecordRepository;
        this.factoryRepository = factoryRepository;
        this.factorySubRepository = factorySubRepository;
        this.workTaskPackingRepository = workTaskPackingRepository;

    }


    @Override
    public CommonResultDto insertShipOrder(ShipOrderDto shipOrderDto) throws Exception {

        Company company = companyRepository.findByUid(shipOrderDto.getCompany_uid());
        User user = userRepository.getById(shipOrderDto.getUser_id());
        Order order = orderRepository.findByUid(shipOrderDto.getOrder_uid());
        Company customer = companyRepository.findByUid(shipOrderDto.getCustomer_uid());

        ShipOrder shipOrder = new ShipOrder();

        CommonResultDto CommonResultDto = new CommonResultDto();

        shipOrder.setOrder(order);
        shipOrder.setCompany(company);
        shipOrder.setCustomer(customer);
        shipOrder.setUser(user);
        shipOrder.setCode(shipOrderDto.getCode());
        shipOrder.setName(shipOrderDto.getName());
        shipOrder.setProduct_spec(shipOrderDto.getProduct_spec());
        shipOrder.setShip_date(shipOrderDto.getShip_date());
        shipOrder.setShip_place(shipOrderDto.getShip_place());
        shipOrder.setOrder_count(shipOrderDto.getOrder_count());
        shipOrder.setDescription(shipOrderDto.getDescription());
        shipOrder.setStatus("출하완료");
        shipOrder.setUsed(Math.toIntExact(shipOrderDto.getUsed()));

            shipOrder.setCreated(LocalDateTime.now());
            shipOrder.setUpdated(LocalDateTime.now());
            ShipOrder insertShipOrder = shipOrderRepository.save(shipOrder);

            Long uid = insertShipOrder.getUid();

            List<Map<String, Object>> shipOrderSubList = shipOrderDto.getShip_order_sub();

            if (shipOrderSubList != null) {
                for (Map<String, Object> shipOrderSubData : shipOrderSubList) {
                    ShipOrderSub shipOrderSub = new ShipOrderSub();
                    shipOrderSub.setShipOrder(insertShipOrder);
                    if(shipOrderSubData.containsKey("stock_uid")){
                        Long stockUid = Long.parseLong(shipOrderSubData.get("stock_uid").toString());
                        Stock selectedStock = stockRepository.findByUid(stockUid);
                        shipOrderSub.setStock(selectedStock);

                    }
                    if(shipOrderSubData.containsKey("item_uid")){
                        Long itemUid = Long.parseLong(shipOrderSubData.get("item_uid").toString());
                        Item selectedItem = itemRepository.findByUid(itemUid);
                        shipOrderSub.setItem(selectedItem);
                    }

                    if (shipOrderSubData.get("unit") != null && !shipOrderSubData.get("unit").toString().isEmpty()) {
                        shipOrderSub.setUnit(shipOrderSubData.get("unit").toString());
                    } else {
                        shipOrderSub.setUnit("");
                    }

                    if (!shipOrderSubData.get("qty").toString().isEmpty()) {
                        try {
                            shipOrderSub.setQty(Double.valueOf(shipOrderSubData.get("qty").toString()));
                        } catch (NumberFormatException e) {
                            shipOrderSub.setQty(0D);
                        }
                    }

                    if (!shipOrderSubData.get("price").toString().isEmpty()) {
                        try {
                            shipOrderSub.setPrice(Integer.valueOf(shipOrderSubData.get("price").toString()));
                        } catch (NumberFormatException e) {
                            shipOrderSub.setPrice(0);
                        }
                    }
                    if (!shipOrderSubData.get("buy_price").toString().isEmpty()) {
                        try {
                            shipOrderSub.setBuy_price(Integer.valueOf(shipOrderSubData.get("buy_price").toString()));
                        } catch (NumberFormatException e) {
                            shipOrderSub.setBuy_price(0);
                        }
                    }
                    if (!shipOrderSubData.get("supply_price").toString().isEmpty()) {
                        try {
                            shipOrderSub.setSupply_price(Integer.valueOf(shipOrderSubData.get("supply_price").toString()));
                        } catch (NumberFormatException e) {
                            shipOrderSub.setSupply_price(0);
                        }
                    }
                    if (!shipOrderSubData.get("vat_price").toString().isEmpty()) {
                        try {
                            shipOrderSub.setVat_price(Integer.valueOf(shipOrderSubData.get("vat_price").toString()));
                        } catch (NumberFormatException e) {
                            shipOrderSub.setVat_price(0);
                        }
                    }
                    shipOrderSub.setCreated(LocalDateTime.now());

                    ShipOrderSub insertShipOrderSub = shipOrderSubRepository.save(shipOrderSub);
                    Long shipOrderSubUid = insertShipOrderSub.getUid();
                    if(shipOrderSubUid  != null){

                        Optional<Stock> selectedStock = stockRepository.findById(String.valueOf(insertShipOrderSub.getStock().getUid()));

                        Order updatedOrder;

                        if (selectedStock.isPresent()) {
                            Stock stock = selectedStock.get();
                            stock.setStatus("출하완료");
                            Stock insertStock = stockRepository.save(stock);
                            Long stockChangeUid = insertStock.getUid();
                            if(stockChangeUid != null){
                                StockRecord stockRecord = new StockRecord();
                                stockRecord.setItem(stock.getItem());
                                stockRecord.setCompany(company);

                                stockRecord.setOutFactory(stock.getFactory());
                                stockRecord.setOutFactorySub(stock.getFactorySub());

                                stockRecord.setInFactory(stock.getFactory());
                                stockRecord.setInFactorySub(stock.getFactorySub());

                                stockRecord.setLot(stock.getLot());
                                stockRecord.setQty(stock.getQty());

                                stockRecord.setUnit(stock.getUnit());
                                stockRecord.setType("출하");
                                stockRecord.setStatus("출하완료");
                                stockRecord.setReason(customer.getName() + "납품용 출하");
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




                }
                setSuccessResult(CommonResultDto);
                return CommonResultDto;

            }else{
                setFailResult(CommonResultDto);
                return CommonResultDto;
            }




    }
    
    


    @Override
    public CommonResultDto updateShipOrder(ShipOrderDto shipOrderDto) throws Exception {

        Company company = companyRepository.findByUid(shipOrderDto.getCompany_uid());
        Optional<ShipOrder> selectedShipOrder = shipOrderRepository.findById(String.valueOf(shipOrderDto.getUid()));

        ShipOrder updatedShipOrder;
        CommonResultDto CommonResultDto = new CommonResultDto();
        if (selectedShipOrder.isPresent()) {
            ShipOrder shipOrder = selectedShipOrder.get();

            shipOrder.setProduct_spec(shipOrderDto.getProduct_spec());
            shipOrder.setShip_place(shipOrderDto.getShip_place());
            shipOrder.setShip_date(shipOrderDto.getShip_date());
            shipOrder.setOrder_count(shipOrderDto.getOrder_count());
            shipOrder.setDescription(shipOrderDto.getDescription());

            shipOrder.setStatus("출하완료");

            shipOrder.setCreated(shipOrder.getCreated());
            shipOrder.setUpdated(LocalDateTime.now());
            shipOrder.setUsed(Math.toIntExact(shipOrderDto.getUsed()));

            updatedShipOrder = shipOrderRepository.save(shipOrder);

            Long uid = updatedShipOrder.getUid();
            if(uid != null){
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
    public List<ShipOrder> selectShipOrder(CommonInfoSearchDto commonInfoSearchDto) {
        return shipOrderRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<ShipOrder> selectTotalShipOrder(CommonSearchDto commonSearchDto) {
        return shipOrderRepository.findAll(commonSearchDto);

    }

    @Override
    public String deleteShipOrder(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<ShipOrder> selectedShipOrder = Optional.ofNullable(shipOrderRepository.findByUid(uid));
            if (selectedShipOrder.isPresent()) {
                ShipOrder shipOrder = selectedShipOrder.get();

                shipOrderRepository.delete(shipOrder);
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