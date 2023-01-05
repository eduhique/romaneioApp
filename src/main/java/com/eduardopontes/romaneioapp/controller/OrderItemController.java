package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.config.ConstantValues;
import com.eduardopontes.romaneioapp.dto.OrderItemDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.service.OrderItemService;
import com.eduardopontes.romaneioapp.util.Util;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/order/")
public class OrderItemController {
    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @PostMapping("{id}/item")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderItemDto save(@PathVariable Long id, @Valid @RequestBody OrderItemDto orderItemDto) {
        return orderItemService.save(id, orderItemDto);
    }

    @PostMapping("{id}/item/all")
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderItemDto> save(@PathVariable Long id, @Valid @RequestBody List<OrderItemDto> orderItemDtos) {
        return orderItemService.saveAll(id, orderItemDtos);
    }

    @PutMapping("item/{id}/detach")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void detachItem(@PathVariable Long id, @Valid @RequestBody boolean value) {
        orderItemService.detachItem(id, value);
    }

    @PutMapping("item/{id}/confer")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void conferItem(@PathVariable Long id, @Valid @RequestBody boolean value) {
        orderItemService.conferItem(id, value);
    }

    @GetMapping("item/{id}")
    public OrderItemDto findById(@PathVariable("id") Long id) {
        return orderItemService.findById(id);
    }

    @GetMapping("{id}/item")
    public PageDto<OrderItemDto> findAll(@PathVariable("id") Long orderId,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_PAGE_NUMBER, required = false) Integer page,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_PAGE_SIZE, required = false) Integer size,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_ORDER_BY, required = false) String order,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_ORDER_DIRECTION, required = false) String direction) {

        Sort.Direction directionEnum = Util.getDirection(direction);


        return orderItemService.findAll(orderId, page, size, new Sort.Order(directionEnum, order));
    }

    @DeleteMapping("item/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        orderItemService.delete(id);
    }


}
