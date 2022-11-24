package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.OrderItemDto;
import com.eduardopontes.romaneioapp.dto.mapper.OrderItemMapper;
import com.eduardopontes.romaneioapp.model.order.Order;
import com.eduardopontes.romaneioapp.model.order.OrderItem;
import com.eduardopontes.romaneioapp.model.product.Product;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import com.eduardopontes.romaneioapp.repository.OrderItemRepository;
import com.eduardopontes.romaneioapp.service.OrderItemService;
import com.eduardopontes.romaneioapp.service.OrderService;
import com.eduardopontes.romaneioapp.service.ProductPrimitiveTypeService;
import com.eduardopontes.romaneioapp.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    public static final String ITEM_NAO_ENCONTRADO = "Item não encontrado.";

    private final OrderItemRepository orderItemRepository;

    private final OrderService orderService;

    private final ProductService productService;

    private final ProductPrimitiveTypeService primitiveTypeService;

    private final OrderItemMapper orderItemMapper;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, OrderService orderService,
            ProductService productService, ProductPrimitiveTypeService primitiveTypeService,
            OrderItemMapper orderItemMapper) {
        this.orderItemRepository = orderItemRepository;
        this.orderService = orderService;
        this.productService = productService;
        this.primitiveTypeService = primitiveTypeService;
        this.orderItemMapper = orderItemMapper;
    }

    @Transactional
    @Override
    public OrderItemDto save(Long orderId, OrderItemDto orderItemDto) {
        Order order = orderService.findOrderById(orderId);
        return save(order, orderItemDto);
    }

    @Transactional
    @Override
    public OrderItemDto save(Order order, OrderItemDto orderItemDto) {
        Product product = productService.findProductById(orderItemDto.getProduct().getId());
        ProductPrimitiveType type =
                primitiveTypeService.findByShortName(orderItemDto.getProductPrimitiveType().getShortName());

        OrderItem orderItem = orderItemMapper.toOrderItem(orderItemDto);
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setProductPrimitiveType(type);

        orderItemRepository.save(orderItem);
        return orderItemMapper.fromOrderItem(orderItem);
    }

    @Transactional
    @Override
    public void update(Long id, OrderItemDto orderItemDto) {
        orderItemRepository.findById(id)
                .map(orderItem -> {
                    Product product = productService.findProductById(orderItemDto.getProduct().getId());
                    ProductPrimitiveType type =
                            primitiveTypeService.findByShortName(orderItemDto.getProductPrimitiveType().getShortName());

                    orderItem.setProduct(product);
                    orderItem.setProductPrimitiveType(type);
                    orderItem.setAmount(orderItemDto.getAmount());
                    orderItem.setDetached(orderItemDto.isDetached());
                    orderItem.setConferred(orderItemDto.isConferred());

                    orderItemRepository.save(orderItem);
                    return orderItem;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ITEM_NAO_ENCONTRADO));

    }

    @Transactional
    @Override
    public void detachItem(Long id, boolean value) {
        orderItemRepository.findById(id)
                .map(orderItem -> {
                    orderItem.setDetached(value);

                    orderItemRepository.save(orderItem);
                    return orderItem;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ITEM_NAO_ENCONTRADO));
    }

    @Transactional
    @Override
    public void conferItem(Long id, boolean value) {
        orderItemRepository.findById(id)
                .map(orderItem -> {
                    orderItem.setConferred(value);

                    orderItemRepository.save(orderItem);
                    return orderItem;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ITEM_NAO_ENCONTRADO));
    }

    @Override
    public OrderItemDto findById(Long id) {
        return orderItemRepository.findById(id)
                .map(orderItemMapper::fromOrderItem)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ITEM_NAO_ENCONTRADO));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        orderItemRepository.findById(id)
                .map(orderItem -> {
                    orderItemRepository.delete(orderItem);
                    return orderItem;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ITEM_NAO_ENCONTRADO));
    }
}
