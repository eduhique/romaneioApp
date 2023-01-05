package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.OrderItemDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.mapper.OrderItemMapper;
import com.eduardopontes.romaneioapp.model.order.Order;
import com.eduardopontes.romaneioapp.model.order.OrderItem;
import com.eduardopontes.romaneioapp.model.product.Product;
import com.eduardopontes.romaneioapp.model.product.ProductPrimitiveType;
import com.eduardopontes.romaneioapp.repository.OrderItemRepository;
import com.eduardopontes.romaneioapp.repository.OrderRepository;
import com.eduardopontes.romaneioapp.service.OrderItemService;
import com.eduardopontes.romaneioapp.service.ProductPrimitiveTypeService;
import com.eduardopontes.romaneioapp.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService {

    private static final String PEDIDO_NAO_ENCONTRADO = "Pedido não encontrado.";

    private static final String ITEM_NAO_ENCONTRADO = "Item não encontrado.";

    private final OrderItemRepository orderItemRepository;

    private final ProductService productService;

    private final ProductPrimitiveTypeService primitiveTypeService;

    private final OrderItemMapper orderItemMapper;

    private final OrderRepository orderRepository;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository,
            ProductService productService, ProductPrimitiveTypeService primitiveTypeService,
            OrderItemMapper orderItemMapper,
            OrderRepository orderRepository) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.primitiveTypeService = primitiveTypeService;
        this.orderItemMapper = orderItemMapper;
        this.orderRepository = orderRepository;
    }

    @Transactional
    @Override
    public OrderItemDto save(Long orderId, OrderItemDto orderItemDto) {
        Order order =
                orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                                                PEDIDO_NAO_ENCONTRADO));
        return save(order, orderItemDto);
    }

    @Transactional
    @Override
    public OrderItemDto save(Order order, OrderItemDto orderItemDto) {
        Product product = productService.findById(orderItemDto.getProduct().getId());
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
    public List<OrderItemDto> saveAll(Long orderId, List<OrderItemDto> orderItemDtoList) {
        Order order =
                orderRepository.findById(orderId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                                                                PEDIDO_NAO_ENCONTRADO));
        return saveAll(order, orderItemDtoList);
    }

    @Transactional
    @Override
    public List<OrderItemDto> saveAll(Order order, List<OrderItemDto> orderItemDtoList) {
        List<OrderItem> orderItems = orderItemDtoList
                .stream()
                .map(orderItemDto -> {
                    Product product = productService.findById(orderItemDto.getProduct().getId());
                    ProductPrimitiveType type =
                            primitiveTypeService.findByShortName(orderItemDto.getProductPrimitiveType().getShortName());

                    OrderItem orderItem = orderItemMapper.toOrderItem(orderItemDto);
                    orderItem.setOrder(order);
                    orderItem.setId(null);
                    orderItem.setProduct(product);
                    orderItem.setProductPrimitiveType(type);

                    return orderItem;
                })
                .collect(Collectors.toList());

        return orderItemRepository.saveAll(orderItems)
                .stream()
                .map(orderItemMapper::fromOrderItem)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void update(Long id, OrderItemDto orderItemDto) {
        orderItemRepository.findById(id)
                .map(orderItem -> {
                    Product product = productService.findById(orderItemDto.getProduct().getId());
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

    @Override
    public PageDto<OrderItemDto> findAll(Long orderId, Integer page, Integer size, Sort.Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<OrderItem> result = orderItemRepository.findAllByOrderId(orderId, pageable);

        return PageDto.<OrderItemDto>builder()
                .data(result.getContent().stream().map(orderItemMapper::fromOrderItem).collect(Collectors.toList()))
                .pageNumber(result.getNumber())
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .last(result.isLast())
                .build();
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

    @Transactional
    @Override
    public void deleteAllByOrderId(Long id) {
        orderItemRepository.deleteAllByOrderId(id);
    }
}
