package com.springboot.new_java.data.dao.impl;

import ch.qos.logback.classic.Logger;
import com.springboot.new_java.common.CommonResponse;
import com.springboot.new_java.data.dao.OrderDAO;
import com.springboot.new_java.data.dto.common.CommonInfoSearchDto;
import com.springboot.new_java.data.dto.common.CommonResultDto;
import com.springboot.new_java.data.dto.common.CommonSearchDto;
import com.springboot.new_java.data.dto.order.OrderDto;
import com.springboot.new_java.data.entity.*;
import com.springboot.new_java.data.repository.company.CompanyRepository;
import com.springboot.new_java.data.repository.estimate.EstimateRepository;
import com.springboot.new_java.data.repository.order.OrderRepository;
import com.springboot.new_java.data.repository.orderSub.OrderSubRepository;
import com.springboot.new_java.data.repository.item.ItemRepository;
import com.springboot.new_java.data.repository.user.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class OrderDAOImpl implements OrderDAO {

    private final Logger LOGGER = (Logger) LoggerFactory.getLogger(OrderDAOImpl.class);

    private final OrderRepository orderRepository;
    private final OrderSubRepository orderSubRepository;
    private final UserRepository userRepository;

    private final ItemRepository itemRepository;

    private final CompanyRepository companyRepository;

    private final EstimateRepository estimateRepository;


    @Autowired
    public OrderDAOImpl(OrderRepository orderRepository,
                        OrderSubRepository orderSubRepository,
                        UserRepository userRepository,
                        ItemRepository itemRepository,
                        CompanyRepository companyRepository,
                        EstimateRepository estimateRepository
                          ) {
        this.orderRepository = orderRepository;
        this.orderSubRepository = orderSubRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.companyRepository = companyRepository;
        this.estimateRepository = estimateRepository;

     
    }

    @Override
    public CommonResultDto insertOrder(OrderDto orderDto) throws Exception {

        Company company = companyRepository.findByUid(orderDto.getCompany_uid());
        Company customer = companyRepository.findByUid(orderDto.getCustomer_uid());
        Estimate estimate = estimateRepository.findByUid(orderDto.getEstimate_uid());


        User user = userRepository.getById(orderDto.getUser_id());


        Order order = new Order();

        order.setUser(user);
        order.setCompany(company);
        order.setCustomer(customer);
        order.setEstimate(estimate);

        order.setCode(orderDto.getCode());
        order.setName(orderDto.getName());
        order.setProduct_spec(orderDto.getProduct_spec());
        order.setShip_place(orderDto.getShip_place());

        order.setDescription(orderDto.getDescription());
        order.setUsed(Math.toIntExact(orderDto.getUsed()));
        order.setCreated(LocalDateTime.now());

        order.setShip_date(orderDto.getShip_date());


        Order insertOrder = orderRepository.save(order);

        Long uid = insertOrder.getUid();

        LOGGER.info("[uid] : {}",uid);
        Order selectedOrder = orderRepository.findByUid(uid);

        List<Map<String, Object>> orderSubList = orderDto.getOrder_sub();

        CommonResultDto CommonResultDto = new CommonResultDto();
        if (orderSubList != null) {

            for (Map<String, Object> orderSubData : orderSubList) {
                OrderSub orderSub = new OrderSub();

                orderSub.setOrder(selectedOrder);
                if (orderSubData.containsKey("item_uid")) {
                    Long itemUid = Long.parseLong(orderSubData.get("item_uid").toString());
                    Item selectedItem = itemRepository.findByUid(itemUid);
                    orderSub.setItem(selectedItem);
                }
                orderSub.setUnit(orderSubData.get("unit").toString());
                orderSub.setQty(Double.valueOf(orderSubData.get("qty").toString()));
                orderSub.setBuy_price(Integer.valueOf(orderSubData.get("buy_price").toString()));
                orderSub.setPrice(Integer.valueOf(orderSubData.get("price").toString()));
                orderSub.setSupply_price(Integer.valueOf(orderSubData.get("supply_price").toString()));
                orderSub.setVat_price(Integer.valueOf(orderSubData.get("vat_price").toString()));
                orderSub.setDescription(orderSubData.get("description").toString());

                orderSub.setCreated(LocalDateTime.now());


                orderSubRepository.save(orderSub);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public CommonResultDto updateOrder(OrderDto orderDto) throws Exception {


        Company company = companyRepository.findByUid(orderDto.getCompany_uid());
        Company customer = companyRepository.findByUid(orderDto.getCustomer_uid());
        Estimate estimate = estimateRepository.findByUid(orderDto.getEstimate_uid());


        User user = userRepository.getById(orderDto.getUser_id());

        
        Optional<Order> selectedOrder = orderRepository.findById(String.valueOf(orderDto.getUid()));

        Order updatedOrder;

        if (selectedOrder.isPresent()) {
            Order order = selectedOrder.get();
            order.setCompany(company);
            order.setCustomer(customer);
            order.setEstimate(estimate);

            order.setUser(user);

            order.setCode(orderDto.getCode());
            order.setName(orderDto.getName());

            order.setProduct_spec(orderDto.getProduct_spec());
            order.setShip_place(orderDto.getShip_place());

            order.setDescription(orderDto.getDescription());

            order.setShip_date(orderDto.getShip_date());


            order.setUsed(Math.toIntExact(orderDto.getUsed()));
            order.setCreated(LocalDateTime.now());
            updatedOrder = orderRepository.save(order);



        } else {
            throw new Exception();
        }


        List<Map<String, Object>> orderSubList = orderDto.getOrder_sub();

        LOGGER.info("[Order] : {}",selectedOrder);
        CommonResultDto CommonResultDto = new CommonResultDto();

        if (orderSubList != null) {

            List<OrderSub> deletedData = orderSubRepository.findByOrderUid(orderDto.getUid());
            orderSubRepository.deleteAll(deletedData);



            for (Map<String, Object> orderSubData : orderSubList) {
                OrderSub orderSub = new OrderSub();
                orderSub.setOrder(updatedOrder);

                if (orderSubData.containsKey("item_uid")) {
                    Long itemUid = Long.parseLong(orderSubData.get("item_uid").toString());
                    Item selectedItem = itemRepository.findByUid(itemUid);
                    orderSub.setItem(selectedItem);
                }


                if (orderSubData.get("unit") != null && !orderSubData.get("unit").toString().isEmpty()) {
                    orderSub.setUnit(orderSubData.get("unit").toString());
                } else {
                    orderSub.setUnit("");
                }
                if (!orderSubData.get("qty").toString().isEmpty()) {
                    try {
                        orderSub.setQty(Double.valueOf(orderSubData.get("qty").toString()));
                    } catch (NumberFormatException e) {
                        orderSub.setQty((double) 0L);
                    }
                }
                if (!orderSubData.get("buy_price").toString().isEmpty()) {
                    try {
                        orderSub.setBuy_price(Integer.valueOf(orderSubData.get("buy_price").toString()));
                    } catch (NumberFormatException e) {
                        orderSub.setBuy_price(0);
                    }
                }
                if (!orderSubData.get("price").toString().isEmpty()) {
                    try {
                        orderSub.setPrice(Integer.valueOf(orderSubData.get("price").toString()));
                    } catch (NumberFormatException e) {
                        orderSub.setPrice(0);
                    }
                }
                if (!orderSubData.get("supply_price").toString().isEmpty()) {
                    try {
                        orderSub.setSupply_price(Integer.valueOf(orderSubData.get("supply_price").toString()));
                    } catch (NumberFormatException e) {
                        orderSub.setSupply_price(0);
                    }
                }
                if (!orderSubData.get("vat_price").toString().isEmpty()) {
                    try {
                        orderSub.setVat_price(Integer.valueOf(orderSubData.get("vat_price").toString()));
                    } catch (NumberFormatException e) {
                        orderSub.setVat_price(0);
                    }
                }
                if (orderSubData.get("description") != null && !orderSubData.get("description").toString().isEmpty()) {
                    orderSub.setDescription(orderSubData.get("description").toString());
                } else {
                    orderSub.setDescription("");
                }

                orderSub.setCreated(LocalDateTime.now());
                orderSub.setUpdated(LocalDateTime.now());

                orderSubRepository.save(orderSub);
            }



            setSuccessResult(CommonResultDto);
            return CommonResultDto;

        }else {
            setFailResult(CommonResultDto);
            return CommonResultDto;

        }
    }


    @Override
    public List<Order> selectOrder(CommonInfoSearchDto commonInfoSearchDto) {
        return orderRepository.findInfo(commonInfoSearchDto);

    }

    @Override
    public List<Order> selectTotalOrder(CommonSearchDto commonSearchDto) {
        return orderRepository.findAll(commonSearchDto);

    }

    @Override
    public String deleteOrder(List<Long> uids) throws Exception {

        for (Long uid : uids) {
            Optional<Order> selectedOrder = Optional.ofNullable(orderRepository.findByUid(uid));
            if (selectedOrder.isPresent()) {
                Order order = selectedOrder.get();
                order.setUsed(0);
                order.setDeleted(LocalDateTime.now());
                orderRepository.save(order);
            } else {
                throw new Exception("Order with UID " + uid + " not found.");
            }
        }
        return "Orders deleted successfully";
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