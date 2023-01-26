package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.config.ConstantValues;
import com.eduardopontes.romaneioapp.dto.OrderDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.mapper.OrderMapper;
import com.eduardopontes.romaneioapp.model.client.Client;
import com.eduardopontes.romaneioapp.model.order.Order;
import com.eduardopontes.romaneioapp.model.romaneio.Romaneio;
import com.eduardopontes.romaneioapp.model.user.User;
import com.eduardopontes.romaneioapp.service.OrderService;
import com.eduardopontes.romaneioapp.util.Util;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    private final OrderService orderService;

    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService,
            OrderMapper orderMapper) {
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto save(@Valid @RequestBody OrderDto orderDto) {
        return orderService.save(orderDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody OrderDto orderDto) {
        orderService.update(id, orderDto);
    }

    @PatchMapping("{id}/next-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@PathVariable Long id) {
        orderService.changeToNextStatus(id);
    }

    @GetMapping("{id}")
    public OrderDto findById(@PathVariable("id") Long id) {
        return orderMapper.fromOrder(orderService.findById(id));
    }

    @GetMapping
    public PageDto<OrderDto> findAll(@RequestParam(required = false) String clientName,
            @RequestParam(required = false) Long user, @RequestParam(required = false) Long romaneio,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_PAGE_NUMBER, required = false) Integer page,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_PAGE_SIZE, required = false) Integer size,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_ORDER_BY, required = false) String order,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_ORDER_DIRECTION, required = false) String direction) {

        Sort.Direction directionEnum = Util.getDirection(direction);

        var orderObject = new Order();
        if (clientName != null) {
            var client = new Client();
            client.setName(clientName);
            orderObject.setClient(client);
        }

        if (user != null) {
            var userObj = new User();
            userObj.setId(user);
            orderObject.setUser(userObj);
        }

        if (romaneio != null) {
            var romaneioObj = new Romaneio();
            romaneioObj.setId(romaneio);
            orderObject.setRomaneio(romaneioObj);
        }
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Order> example = Example.of(orderObject, matcher);

        return orderService.findAll(example, page, size, new Sort.Order(directionEnum, order));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderService.delete(id);
    }


}
