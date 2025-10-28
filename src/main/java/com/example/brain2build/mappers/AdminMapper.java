package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Admin;
import com.example.brain2build.domain.dto.Admin.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdminMapper {

    Admin toEntity(AdminCreateUpdateDto dto);

    AdminReadDto toReadDto(Admin admin);

    Set<AdminReadDto> toReadDtoSet(Set<Admin> admins);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(AdminCreateUpdateDto dto, @MappingTarget Admin admin);
}
