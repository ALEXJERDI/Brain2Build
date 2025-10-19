package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.domain.dto.Idea.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface IdeaMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    Idea toEntity(IdeaCreateUpdateDto dto);

    IdeaReadDto toReadDto(Idea idea);
    Set<IdeaReadDto> toReadDtoSet(Set<Idea> ideas);

    // ✅ ajout utile pour vues légères
    IdeaSimpleDto toSimpleDto(Idea idea);
    Set<IdeaSimpleDto> toSimpleDtoSet(Set<Idea> ideas);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(IdeaCreateUpdateDto dto, @MappingTarget Idea entity);

}
