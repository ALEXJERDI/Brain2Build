package org.example.ideaservice.mapper;

import org.example.ideaservice.dto.idea.IdeaCreateDto;
import org.example.ideaservice.dto.idea.IdeaReadDto;
import org.example.ideaservice.dto.idea.IdeaUpdateDto;
import org.example.ideaservice.entity.Idea;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface IdeaMapper {

    // --------------------------
    // ENTITY → READ DTO
    // --------------------------
    IdeaReadDto toReadDto(Idea idea);

    // --------------------------
    // CREATE DTO → ENTITY
    // --------------------------
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "feedback", ignore = true)
    Idea fromCreateDto(IdeaCreateDto dto);

    // --------------------------
    // PARTIAL UPDATE
    // --------------------------
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(@MappingTarget Idea idea, IdeaUpdateDto dto);
}
