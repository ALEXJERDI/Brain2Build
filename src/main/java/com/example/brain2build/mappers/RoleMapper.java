package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.domain.dto.Role.RoleDto;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper {

    Role toEntity(RoleDto dto);

    RoleDto toDto(Role role);

    Set<RoleDto> toDtoSet(Set<Role> roles);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(RoleDto dto, @MappingTarget Role role);
}
