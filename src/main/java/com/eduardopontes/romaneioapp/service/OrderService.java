package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.OrderDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.model.order.Order;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

public interface OrderService {

    OrderDto save(OrderDto orderDto);

    void update(Long id, OrderDto orderDto);

    void changeToNextStatus(Long id);

    Order findById(Long id);

    PageDto<OrderDto> findAll(Example<Order> filter, Integer page, Integer size, Sort.Order order);

    void delete(Long id);
}
