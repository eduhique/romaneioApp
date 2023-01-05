package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.RomaneioDto;
import com.eduardopontes.romaneioapp.dto.mapper.RomaneioMapper;
import com.eduardopontes.romaneioapp.exception.BadRequestException;
import com.eduardopontes.romaneioapp.model.romaneio.Romaneio;
import com.eduardopontes.romaneioapp.model.romaneio.RomaneioStatus;
import com.eduardopontes.romaneioapp.repository.RomaneioRepository;
import com.eduardopontes.romaneioapp.service.RomaneioService;
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
import java.util.stream.Collectors;

@Service
public class RomaneioServiceImpl implements RomaneioService {

    public static final String ROMANEIO_NAO_ENCONTRADO = "Romaneio não encontrado.";

    private final RomaneioRepository romaneioRepository;

    private final RomaneioMapper romaneioMapper;

    public RomaneioServiceImpl(RomaneioRepository romaneioRepository, RomaneioMapper romaneioMapper) {
        this.romaneioRepository = romaneioRepository;
        this.romaneioMapper = romaneioMapper;
    }

    @Transactional
    @Override
    public RomaneioDto save(RomaneioDto romaneioDto) {
        Romaneio romaneio = romaneioMapper.toRomaneio(romaneioDto);
        romaneio.setStatus(RomaneioStatus.CRIADO);
        romaneio.setStatusDate(LocalDateTime.now());
        romaneioRepository.save(romaneio);
        return romaneioMapper.fromRomaneio(romaneio);
    }

    @Transactional
    @Override
    public void update(Long id, RomaneioDto romaneioDto) {
        romaneioRepository.findById(id)
                .map(romaneio -> {
                    romaneio.setName(romaneioDto.getName());
                    romaneio.setActive(romaneioDto.getActive());
                    romaneio.setComments(romaneioDto.getComments());

                    return romaneio;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROMANEIO_NAO_ENCONTRADO));
    }

    @Transactional
    @Override
    public void changeToNextStatus(Long id) {
        romaneioRepository.findById(id)
                .map(romaneio -> {
                    switch (romaneio.getStatus()) {
                        case CRIADO:
                            romaneio.setStatus(RomaneioStatus.EM_ANDAMENTO);
                            break;
                        case EM_ANDAMENTO:
                            romaneio.setStatus(RomaneioStatus.FINALIZADO);
                            break;
                        case FINALIZADO:
                            throw new BadRequestException("Romaneio Finalizado.");
                        case CANCELADO:
                            throw new BadRequestException("Romaneio está cancelado.");
                    }
                    romaneio.setStatusDate(LocalDateTime.now());
                    romaneioRepository.save(romaneio);
                    return romaneio;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROMANEIO_NAO_ENCONTRADO));
    }

    @Transactional
    @Override
    public void cancelRomaneio(Long id) {
        romaneioRepository.findById(id)
                .map(romaneio -> {
                    if (romaneio.getStatus().equals(RomaneioStatus.CANCELADO))
                        throw new BadRequestException("Romaneio já está cancelado.");
                    romaneio.setStatus(RomaneioStatus.CANCELADO);
                    romaneio.setStatusDate(LocalDateTime.now());
                    romaneioRepository.save(romaneio);
                    return romaneio;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROMANEIO_NAO_ENCONTRADO));
    }

    @Transactional
    @Override
    public void modifyStatus(Long id, RomaneioStatus romaneioStatus) {
        romaneioRepository.findById(id)
                .map(romaneio -> {
                    if (!romaneio.getStatus().equals(romaneioStatus)) {
                        romaneio.setStatus(romaneioStatus);
                        romaneio.setStatusDate(LocalDateTime.now());
                        romaneioRepository.save(romaneio);
                    }
                    return romaneio;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROMANEIO_NAO_ENCONTRADO));
    }

    @Override
    public PageDto<RomaneioDto> findAll(Example<Romaneio> filter, Integer page, Integer size, Sort.Order order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));

        Page<Romaneio> result = romaneioRepository.findAll(filter, pageable);

        return PageDto.<RomaneioDto>builder()
                .data(result.getContent().stream().map(romaneioMapper::fromRomaneio).collect(Collectors.toList()))
                .pageNumber(result.getNumber())
                .pageSize(result.getSize())
                .totalElements(result.getTotalElements())
                .totalPages(result.getTotalPages())
                .last(result.isLast())
                .build();
    }

    @Override
    public Romaneio findById(Long id) {
        return romaneioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROMANEIO_NAO_ENCONTRADO));
    }

    @Transactional
    @Override
    public void delete(Long id) {
        romaneioRepository.findById(id)
                .map(romaneio -> {
                    romaneioRepository.delete(romaneio);

                    return romaneio;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROMANEIO_NAO_ENCONTRADO));
    }
}
