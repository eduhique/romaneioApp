package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.OrderDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.mapper.OrderMapper;
import com.eduardopontes.romaneioapp.exception.BadRequestException;
import com.eduardopontes.romaneioapp.model.order.Order;
import com.eduardopontes.romaneioapp.model.order.OrderStatus;
import com.eduardopontes.romaneioapp.repository.OrderRepository;
import com.eduardopontes.romaneioapp.service.ClientService;
import com.eduardopontes.romaneioapp.service.OrderItemService;
import com.eduardopontes.romaneioapp.service.OrderService;
import com.eduardopontes.romaneioapp.service.RomaneioService;
import com.eduardopontes.romaneioapp.service.UserService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String PEDIDO_NAO_ENCONTRADO = "Pedido não encontrado.";

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

    @Transactional
    @Override
    public OrderDto save(OrderDto orderDto) {
        Order order = orderMapper.toOrder(orderDto);
        order.setRomaneio(romaneioService.findById(orderDto.getRomaneio().getId()));
        order.setClient(clientService.findById(orderDto.getClient().getId()));
        order.setUser(userService.findById(orderDto.getUser().getId()));
        orderRepository.save(order);
        orderItemService.saveAll(order, new ArrayList<>(orderDto.getOrderItems()));

        return orderMapper.fromOrder(order);
    }

    @Transactional
    @Override
    public void update(Long id, OrderDto orderDto) {
        orderRepository.findById(id)
                .map(order -> {
                    if (!Objects.equals(order.getRomaneio().getId(), orderDto.getRomaneio().getId()))
                        order.setRomaneio(romaneioService.findById(orderDto.getRomaneio().getId()));
                    if (!Objects.equals(order.getClient().getId(), orderDto.getClient().getId()))
                        order.setClient(clientService.findById(orderDto.getClient().getId()));
                    if (!Objects.equals(order.getUser().getId(), orderDto.getUser().getId()))
                        order.setUser(userService.findById(orderDto.getUser().getId()));
                    if (!order.getStatus().equals(orderDto.getStatus())) {
                        order.setStatus(orderDto.getStatus());
                        order.setStatusDate(LocalDateTime.now());
                    }

                    orderItemService.deleteAllByOrderId(orderDto.getId());
                    orderItemService.saveAll(order, new ArrayList<>(orderDto.getOrderItems()));

                    orderRepository.save(order);

                    return order;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                               PEDIDO_NAO_ENCONTRADO));
    }

    @Transactional
    @Override
    public void changeToNextStatus(Long id) {
        orderRepository.findById(id)
                .map(order -> {
                    switch (order.getStatus()) {
                        case REALIZADO:
                            order.setStatus(OrderStatus.SEPARANDO);
                            break;
                        case SEPARANDO:
                            order.setStatus(OrderStatus.EM_FATURAMENTO);
                            break;
                        case EM_FATURAMENTO:
                            order.setStatus(OrderStatus.FATURADO);
                            break;
                        case FATURADO:
                            order.setStatus(OrderStatus.ENTREGUE);
                            break;
                        case ENTREGUE:
                            throw new BadRequestException("Pedido já está Entregue.");
                        case CANCELADO:
                            throw new BadRequestException("Pedido já está cancelado.");
                    }
                    order.setStatusDate(LocalDateTime.now());
                    orderRepository.save(order);
                    return order;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                               PEDIDO_NAO_ENCONTRADO));

    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PEDIDO_NAO_ENCONTRADO));
    }

    @Override
    public PageDto<OrderDto> findAll(Example<Order> filter, Integer page, Integer size, Sort.Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<Order> result = orderRepository.findAll(filter, pageable);

        return PageDto.<OrderDto>builder()
                .data(result.getContent().stream().map(orderMapper::fromOrder).collect(Collectors.toList()))
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
        orderRepository.findById(id)
                .map(order -> {
                    orderRepository.delete(order);

                    return order;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, PEDIDO_NAO_ENCONTRADO));
    }
}
