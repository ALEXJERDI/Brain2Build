package org.example.userservice.mapper;

import org.example.userservice.domain.dto.IdeatorProfile.IdeatorProfileReadDto;
import org.example.userservice.domain.dto.IdeatorProfile.IdeatorProfileUpdateDto;
import org.example.userservice.domain.entity.IdeatorProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IdeatorMapper {

    @Mapping(target = "userId",    expression = "java(profile.getUser().getId())")
    @Mapping(target = "email",     expression = "java(profile.getUser().getEmail())")
    @Mapping(target = "nom",       expression = "java(profile.getUser().getNom())")
    @Mapping(target = "prenom",    expression = "java(profile.getUser().getPrenom())")
    @Mapping(target = "bio",       source      = "bio")
    @Mapping(target = "ideaCount", source      = "ideaCount")
    IdeatorProfileReadDto toReadDto(IdeatorProfile profile);

    // 🔥 IMPORTANT: add partial update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfile(IdeatorProfileUpdateDto dto, @MappingTarget IdeatorProfile entity);
}
