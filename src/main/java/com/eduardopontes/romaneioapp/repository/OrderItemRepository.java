package com.eduardopontes.romaneioapp.repository;

import com.eduardopontes.romaneioapp.model.order.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Page<OrderItem> findAllByOrderId(Long orderId, Pageable pageable);

    void deleteAllByOrderId(Long id);
}