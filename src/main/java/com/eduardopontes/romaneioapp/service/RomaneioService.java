package com.eduardopontes.romaneioapp.service;

import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.RomaneioDto;
import com.eduardopontes.romaneioapp.dto.RomaneioReportDTO;
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

    void setActive(Long id);

    Romaneio findById(Long id);

    Romaneio findActive();

    PageDto<RomaneioDto> findAll(Example<Romaneio> filter, Integer page, Integer size, Sort.Order order);

    RomaneioReportDTO report(Long id);

    void delete(Long id);

}
