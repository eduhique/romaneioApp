package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.RomaneioDto;
import com.eduardopontes.romaneioapp.model.romaneio.Romaneio;
import com.eduardopontes.romaneioapp.model.romaneio.RomaneioStatus;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;

public interface RomaneioService {

    RomaneioDto save(RomaneioDto romaneioDto);

    void update(Long id, RomaneioDto romaneioDto);

    void changeToNextStatus(Long id);

    void cancelRomaneio(Long id);

    void modifyStatus(Long id, RomaneioStatus romaneioStatus);

    Romaneio findById(Long id);

    PageDto<RomaneioDto> findAll(Example<Romaneio> filter, Integer page, Integer size, Sort.Order order);

    void delete(Long id);

}
