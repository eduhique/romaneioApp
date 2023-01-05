package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.ClientDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.model.client.Client;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

public interface ClientService {

    ClientDto save(ClientDto clientDto);

    Client findById(Long id);

    PageDto<ClientDto> findAll(Example<Client> filter, Integer page, Integer size, Sort.Order order);

    void update(Long id, ClientDto clientDto);

    void delete(Long id);
}
