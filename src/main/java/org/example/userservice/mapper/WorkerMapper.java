package org.example.userservice.mapper;

import org.example.userservice.domain.dto.WorkerProfile.WorkerProfileReadDto;
import org.example.userservice.domain.dto.WorkerProfile.WorkerProfileUpdateDto;
import org.example.userservice.domain.entity.WorkerProfile;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WorkerMapper {

    @Mapping(target = "userId",      expression = "java(profile.getUser().getId())")
    @Mapping(target = "email",       expression = "java(profile.getUser().getEmail())")
    @Mapping(target = "nom",         expression = "java(profile.getUser().getNom())")
    @Mapping(target = "prenom",      expression = "java(profile.getUser().getPrenom())")
    @Mapping(target = "domaine",     source      = "domaine")
    @Mapping(target = "specialite",  source      = "specialite")
    @Mapping(target = "experience",  source      = "experience")
    @Mapping(target = "portfolioUrl",source      = "portfolioUrl")
    WorkerProfileReadDto toReadDto(WorkerProfile profile);

    // 🔥 IMPORTANT: add partial update
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProfile(WorkerProfileUpdateDto dto, @MappingTarget WorkerProfile entity);
}
