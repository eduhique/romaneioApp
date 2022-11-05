package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.ClientDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.mapper.ClientMapper;
import com.eduardopontes.romaneioapp.model.client.Client;
import com.eduardopontes.romaneioapp.repository.ClientRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ClientService {

    private final ClientRepository clientRepository;

    private final ClientMapper clientMapper;

    public ClientService(ClientRepository clientRepository, ClientMapper clientMapper) {
        this.clientRepository = clientRepository;
        this.clientMapper = clientMapper;
    }

    public ClientDto save(ClientDto clientDto) {
        Client client = clientMapper.toClient(clientDto);
        clientRepository.save(client);
        return clientMapper.fromClient(client);
    }

    public ClientDto findById(Long id) {
        return clientRepository.findById(id)
                .map(clientMapper::fromClient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado."));

    }

    public PageDto<Client> findAll(Example<Client> filter, Integer page, Integer size, Sort.Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<Client> result = clientRepository.findAll(filter, pageable);

        return PageDto.<Client>builder()
                .data(result.getContent())
                .pageNumber(result.getNumber())
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .last(result.isLast())
                .build();
    }
}
