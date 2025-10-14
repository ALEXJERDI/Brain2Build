package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.User;
import com.example.brain2build.domain.dto.User.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { RoleMapper.class }
)
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toEntity(UserCreateUpdateDto dto);

    @Mapping(target = "roles", source = "roles")
    UserReadDto toReadDto(User user);

    Set<UserReadDto> toReadDtoSet(Set<User> users);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    User partialUpdate(UserCreateUpdateDto dto, @MappingTarget User user);
}
