package org.example.userservice.mapper;

import org.example.userservice.domain.dto.User.UserReadDto;
import org.example.userservice.domain.dto.User.UserUpdateDto;
import org.example.userservice.domain.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserReadDto toReadDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserUpdateDto dto, @MappingTarget User user);
}

