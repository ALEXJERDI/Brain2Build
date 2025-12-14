package org.example.ideaservice.mapper;

import org.example.ideaservice.dto.request.RequestCreateDto;
import org.example.ideaservice.dto.request.RequestReadDto;
import org.example.ideaservice.dto.request.RequestUpdateDto;
import org.example.ideaservice.entity.Request;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RequestMapper {

    // --------------------------
    // ENTITY → READ DTO
    // --------------------------
    RequestReadDto toReadDto(Request request);

    // --------------------------
    // CREATE DTO → ENTITY
    // --------------------------
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Request fromCreateDto(RequestCreateDto dto);

    // --------------------------
    // PARTIAL UPDATE
    // --------------------------
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Request request, RequestUpdateDto dto);
}
