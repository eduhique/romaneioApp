package com.eduardopontes.romaneioapp.service.impl;

import com.eduardopontes.romaneioapp.dto.OrderDto;
import com.eduardopontes.romaneioapp.dto.PageDto;
import com.eduardopontes.romaneioapp.dto.ProductClientReportDto;
import com.eduardopontes.romaneioapp.dto.ProductReportDto;
import com.eduardopontes.romaneioapp.dto.RomaneioDto;
import com.eduardopontes.romaneioapp.dto.RomaneioReportDTO;
import com.eduardopontes.romaneioapp.dto.mapper.RomaneioMapper;
import com.eduardopontes.romaneioapp.exception.BadRequestException;
import com.eduardopontes.romaneioapp.model.order.Order;
import com.eduardopontes.romaneioapp.model.romaneio.Romaneio;
import com.eduardopontes.romaneioapp.model.romaneio.RomaneioStatus;
import com.eduardopontes.romaneioapp.repository.RomaneioRepository;
import com.eduardopontes.romaneioapp.service.OrderService;
import com.eduardopontes.romaneioapp.service.RomaneioService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RomaneioServiceImpl implements RomaneioService {

    public static final String ROMANEIO_NAO_ENCONTRADO = "Romaneio não encontrado.";

    private final RomaneioRepository romaneioRepository;

    private final RomaneioMapper romaneioMapper;

    private final OrderService orderService;

    public RomaneioServiceImpl(RomaneioRepository romaneioRepository, RomaneioMapper romaneioMapper,
            OrderService orderService) {
        this.romaneioRepository = romaneioRepository;
        this.romaneioMapper = romaneioMapper;
        this.orderService = orderService;
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
    public void setActive(Long id) {
        var romaneio = findById(id);
        var romaneioDeactivate = romaneioRepository.findByActiveIsTrue();


        if (!romaneioDeactivate.isEmpty()) {
            romaneioDeactivate.forEach(romaneio1 -> {
                if (!romaneio1.getId().equals(romaneio.getId())) {
                    romaneio1.setActive(false);
                }
            });

            romaneioRepository.saveAll(romaneioDeactivate);
        }

        if (!romaneio.getActive()) {
            romaneio.setActive(true);
            romaneioRepository.save(romaneio);
        }
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
    public RomaneioReportDTO report(Long id) {
        Romaneio romaneio = findById(id);
        ArrayList<List<ProductClientReportDto>> listOfProductClientReportDtoList =
                getListOfProductClientReportDtoList(romaneio);

        List<ProductReportDto> productReportDtoList =
                getProductReportDtoList(listOfProductClientReportDtoList);

        return RomaneioReportDTO.builder()
                .romaneio(romaneioMapper.fromRomaneio(romaneio))
                .productReports(productReportDtoList)
                .build();
    }


    @Override
    public Romaneio findById(Long id) {
        return romaneioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, ROMANEIO_NAO_ENCONTRADO));
    }

    @Override
    public Romaneio findActive() {
        return romaneioRepository.findByActiveIsTrue()
                .stream().findAny()
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

    private ArrayList<List<ProductClientReportDto>> getListOfProductClientReportDtoList(Romaneio romaneio) {
        int orderPage = 0;
        PageDto<OrderDto> orders = ordersPage(romaneio, orderPage);

        Map<Long, List<ProductClientReportDto>> productMap = new HashMap<>();

        do {
            orders.getData().forEach(orderDto -> {
                var productClientReportDtoStream =
                        orderDto.getOrderItems().stream().map(orderItemDto -> ProductClientReportDto.builder()
                                .client(orderDto.getClient())
                                .orderItem(orderItemDto)
                                .build()).collect(Collectors.groupingBy(
                                productClientReportDto -> productClientReportDto.getOrderItem().getProduct().getId()));
                productClientReportDtoStream.forEach((key, list) -> {
                    if (productMap.containsKey(key)) {
                        var listMap = productMap.get(key);
                        listMap.addAll(list);
                        productMap.put(key, listMap);
                    } else {
                        productMap.put(key, list);
                    }
                });
            });

            orders = ordersPage(romaneio, ++orderPage);
        } while (!orders.isLast());
        return new ArrayList<>(productMap.values());
    }

    private PageDto<OrderDto> ordersPage(Romaneio romaneio, int orderPage) {
        var order = new Order();
        order.setRomaneio(romaneio);

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING);
        Example<Order> example = Example.of(order, matcher);


        return orderService.findAll(example, orderPage, 50, new Sort.Order(Sort.Direction.ASC, "id"));
    }

    private List<ProductReportDto> getProductReportDtoList(
            ArrayList<List<ProductClientReportDto>> listOfProductClientReportDtoList) {
        return listOfProductClientReportDtoList.stream().map(clientReportDtoList -> {
            var result = new ProductReportDto();

            if (!clientReportDtoList.isEmpty())
                result.setProduct(clientReportDtoList.get(0).getOrderItem().getProduct());

            var quantity = clientReportDtoList.stream().mapToDouble(value -> {
                if (value.getOrderItem().getProduct().getProductType().getProductPrimitiveType().getId()
                        .equals(value.getOrderItem().getProductPrimitiveType().getId())) {
                    return value.getOrderItem().getAmount();
                } else {
                    var conversion =
                            value.getOrderItem().getProduct().getProductType().getProductConversionTypes().stream()
                                    .filter(productConversionTypeDto -> productConversionTypeDto.getTargetProductPrimitiveType()
                                            .getId().equals(value.getOrderItem().getProductPrimitiveType().getId()))
                                    .findAny()
                                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                                                                                   "Ocorreu um erro na geração do Report."));
                    return (value.getOrderItem().getAmount() * conversion.getFromPrimary()) /
                           conversion.getToTarget();
                }
            }).reduce(0, Double::sum);

            result.setQuantity(quantity);
            result.setProductClientReports(clientReportDtoList);
            return result;
        }).collect(Collectors.toList());
    }
}
