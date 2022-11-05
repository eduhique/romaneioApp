package com.eduardopontes.romaneioapp.dto.mapper;

import com.eduardopontes.romaneioapp.dto.OrderItemDto;
import com.eduardopontes.romaneioapp.model.order.OrderItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring", uses = {ProductMapper.class,
        ProductPrimitiveTypeMapper.class})
@Component
public interface OrderItemMapper {

    @Mapping(target = "order", ignore = true)
    @Mapping(target = "id", ignore = true)
    OrderItem toOrderItem(OrderItemDto orderItemDto);

    @InheritInverseConfiguration
    OrderItemDto fromOrderItem(OrderItem orderItem);
}
