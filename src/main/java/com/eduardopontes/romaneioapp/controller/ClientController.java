package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.config.ConstantsValues;
import com.eduardopontes.romaneioapp.dto.ClientDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.model.client.Client;
import com.eduardopontes.romaneioapp.service.ClientService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto save(@Valid @RequestBody ClientDto client) {
        return clientService.save(client);
    }

    @GetMapping("{id}")
    public ClientDto findById(@PathVariable("id") Long id) {
        return clientService.findById(id);
    }

    @GetMapping
    public PageDto<Client> findAll(Client client,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_NUMBER, required = false) Integer page,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_PAGE_SIZE, required = false) Integer size,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_ORDER_BY, required = false) String order,
            @RequestParam(defaultValue = ConstantsValues.DEFAULT_ORDER_DIRECTION, required = false) String direction) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Client> example = Example.of(client, matcher);
        return clientService.findAll(example, page, size, new Sort.Order(Sort.Direction.valueOf(direction), order));
    }

}
