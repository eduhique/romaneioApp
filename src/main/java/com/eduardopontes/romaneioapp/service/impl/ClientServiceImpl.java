package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.ClientDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.mapper.ClientMapper;
import com.eduardopontes.romaneioapp.model.client.Client;
import com.eduardopontes.romaneioapp.repository.ClientRepository;
import com.eduardopontes.romaneioapp.service.ClientService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {

    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado.";

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    @Transactional
    @Override
    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.toClient(clientDto);
        clientRepository.save(client);
        return clientMapper.fromClient(client);
    }

    @Transactional
    @Override
    public void update(Long id, ClientDto clientDto) {
        clientRepository
                .findById(id).map(client -> {
                    client.setName(clientDto.getName());
                    client.setDistrict(clientDto.getDistrict());
                    client.setClientType(clientDto.getClientType());
                    clientRepository.save(client);
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NAO_ENCONTRADO));
    }

    @Override
    public Client findById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NAO_ENCONTRADO));

    }

    @Override
    public PageDto<ClientDto> findAll(Example<Client> filter, Integer page, Integer size, Sort.Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<Client> result = clientRepository.findAll(filter, pageable);

        return PageDto.<ClientDto>builder()
                .data(result.getContent().stream().map(clientMapper::fromClient).collect(Collectors.toList()))
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
        clientRepository
                .findById(id).map(client -> {
                    clientRepository.delete(client);
                    return client;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, CLIENTE_NAO_ENCONTRADO));
    }
}
