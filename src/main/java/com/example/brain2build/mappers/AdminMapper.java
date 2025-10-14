package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Admin;
import com.example.brain2build.domain.dto.Admin.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { RoleMapper.class }
)
public interface AdminMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Admin toEntity(AdminCreateUpdateDto dto);

    @Mapping(target = "roles", source = "roles")
    AdminReadDto toReadDto(Admin admin);

    Set<AdminReadDto> toReadDtoSet(Set<Admin> admins);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    Admin partialUpdate(AdminCreateUpdateDto dto, @MappingTarget Admin admin);
}

