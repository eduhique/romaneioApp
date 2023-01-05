package com.eduardopontes.romaneioapp.controller;

import com.eduardopontes.romaneioapp.config.ConstantValues;
import com.eduardopontes.romaneioapp.dto.ClientDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.mapper.ClientMapper;
import com.eduardopontes.romaneioapp.model.client.Client;
import com.eduardopontes.romaneioapp.service.ClientService;
import com.eduardopontes.romaneioapp.util.Util;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
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

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

    private final ClientService clientService;

    private final ClientMapper clientMapper;

    public ClientController(ClientService clientService,
            ClientMapper clientMapper) {
        this.clientService = clientService;
        this.clientMapper = clientMapper;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClientDto save(@Valid @RequestBody ClientDto client) {
        return clientService.save(client);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Long id, @Valid @RequestBody ClientDto client) {
        clientService.update(id, client);
    }

    @GetMapping("{id}")
    public ClientDto findById(@PathVariable("id") Long id) {
        return clientMapper.fromClient(clientService.findById(id));
    }

    @GetMapping
    public PageDto<ClientDto> findAll(Client client,
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
        Example<Client> example = Example.of(client, matcher);

        return clientService.findAll(example, page, size, new Sort.Order(directionEnum, order));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        clientService.delete(id);
    }

}
