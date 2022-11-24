package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.OrderDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.mapper.OrderMapper;
import com.eduardopontes.romaneioapp.model.order.Order;
import com.eduardopontes.romaneioapp.model.order.OrderStatus;
import com.eduardopontes.romaneioapp.repository.OrderRepository;
import com.eduardopontes.romaneioapp.service.ClientService;
import com.eduardopontes.romaneioapp.service.OrderItemService;
import com.eduardopontes.romaneioapp.service.OrderService;
import com.eduardopontes.romaneioapp.service.RomaneioService;
import com.eduardopontes.romaneioapp.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final RomaneioService romaneioService;

    private final ClientService clientService;

    private final UserService userService;

    private final OrderItemService orderItemService;

    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, RomaneioService romaneioService,
            ClientService clientService, UserService userService, OrderItemService orderItemService,
            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.romaneioService = romaneioService;
        this.clientService = clientService;
        this.userService = userService;
        this.orderItemService = orderItemService;
        this.orderMapper = orderMapper;
    }


    @Override
    public OrderDto save(OrderDto orderDto) {
        return null;
    }

    @Override
    public void update(Long id, OrderDto orderDto) {

    }

    @Override
    public void changeToNextStatus(Long id) {

    }

    @Override
    public void cancelOrder(Long id) {

    }

    @Override
    public void modifyStatus(Long id, OrderStatus orderStatus) {

    }

    @Override
    public Order findOrderById(Long id) {
        return null;
    }

    @Override
    public OrderDto findById(Long id) {
        return null;
    }

    @Override
    public PageDto<OrderDto> findAll(Example<Order> filter, Integer page, Integer size, Sort.Order order) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
