package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.OrderItemDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.model.order.Order;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface OrderItemService {

    OrderItemDto save(Long orderId, OrderItemDto orderItemDto);

    OrderItemDto save(Order order, OrderItemDto orderItemDto);

    List<OrderItemDto> saveAll(Long orderId, List<OrderItemDto> orderItemDtoList);

    List<OrderItemDto> saveAll(Order order, List<OrderItemDto> orderItemDtoList);

    void update(Long id, OrderItemDto orderItemDto);

    void detachItem(Long id, boolean value);

    void conferItem(Long id, boolean value);

    OrderItemDto findById(Long id);

    PageDto<OrderItemDto> findAll(Long orderId, Integer page, Integer size, Sort.Order order);

    void delete(Long id);

    void deleteAllByOrderId(Long id);

}
