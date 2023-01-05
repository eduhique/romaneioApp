package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.config.ConstantValues;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.RomaneioDto;
import com.eduardopontes.romaneioapp.dto.mapper.RomaneioMapper;
import com.eduardopontes.romaneioapp.model.romaneio.Romaneio;
import com.eduardopontes.romaneioapp.model.romaneio.RomaneioStatus;
import com.eduardopontes.romaneioapp.service.RomaneioService;
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
@RequestMapping(value = "/romaneio")
public class RomaneioController {

    private final RomaneioService romaneioService;

    private final RomaneioMapper romaneioMapper;

    public RomaneioController(RomaneioService romaneioService,
            RomaneioMapper romaneioMapper) {
        this.romaneioService = romaneioService;
        this.romaneioMapper = romaneioMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RomaneioDto save(@Valid @RequestBody RomaneioDto romaneioDto) {
        return romaneioService.save(romaneioDto);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody RomaneioDto romaneioDto) {
        romaneioService.update(id, romaneioDto);
    }

    @PatchMapping("{id}/next-status")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changeStatus(@PathVariable Long id) {
        romaneioService.changeToNextStatus(id);
    }

    @PatchMapping("{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelRomaneio(@PathVariable Long id) {
        romaneioService.cancelRomaneio(id);
    }

    @PatchMapping("{id}/change-status/{status}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void modifyStatus(@PathVariable Long id, @PathVariable RomaneioStatus status) {
        romaneioService.modifyStatus(id, status);
    }

    @GetMapping
    public PageDto<RomaneioDto> findAll(Romaneio romaneio,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_PAGE_NUMBER, required = false) Integer page,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_PAGE_SIZE, required = false) Integer size,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_ORDER_BY, required = false) String order,
            @RequestParam(defaultValue = ConstantValues.DEFAULT_ORDER_DIRECTION, required = false) String direction) {

        Sort.Direction directionEnum = Util.getDirection(direction);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Romaneio> example = Example.of(romaneio, matcher);

        return romaneioService.findAll(example, page, size, new Sort.Order(directionEnum, order));
    }

    @GetMapping("{id}")
    public RomaneioDto findById(@PathVariable("id") Long id) {
        return romaneioMapper.fromRomaneio(romaneioService.findById(id));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        romaneioService.delete(id);
    }
}
