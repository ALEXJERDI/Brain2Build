package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.domain.dto.Idea.*;
import org.mapstruct.*;

import java.util.Set;@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IdeaMapper {

    Idea toEntity(IdeaCreateUpdateDto dto);

    // Ensure that MapStruct knows how to map 'status'
    @Mapping(source = "status", target = "status")
    IdeaReadDto toReadDto(Idea idea);

    Set<IdeaReadDto> toReadDtoSet(Set<Idea> ideas);

    IdeaSimpleDto toSimpleDto(Idea idea);

    Set<IdeaSimpleDto> toSimpleDtoSet(Set<Idea> ideas);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(IdeaUpdateDto  dto, @MappingTarget Idea entity);
}
