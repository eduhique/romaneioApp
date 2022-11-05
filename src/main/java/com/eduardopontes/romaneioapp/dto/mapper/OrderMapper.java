package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.OrderDto;
import com.eduardopontes.romaneioapp.model.order.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RomaneioMapper.class, ClientMapper.class, UserMapper.class})
public interface OrderMapper {

    @Mapping(target = "id", ignore = true)
    Order toOrder(OrderDto orderDto);

    @InheritInverseConfiguration
    OrderDto fromOrder(Order order);
}
