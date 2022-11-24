package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.OrderItemDto;
import com.eduardopontes.romaneioapp.model.order.Order;

public interface OrderItemService {

    OrderItemDto save(Long orderId, OrderItemDto orderItemDto);

    OrderItemDto save(Order order, OrderItemDto orderItemDto);

    void update(Long id, OrderItemDto orderItemDto);

    void detachItem(Long id, boolean value);

    void conferItem(Long id, boolean value);

    OrderItemDto findById(Long id);

    void delete(Long id);
}
