package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Ideator;
import com.example.brain2build.domain.dto.Ideator.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IdeatorMapper {

    Ideator toEntity(IdeatorCreateUpdateDto dto);

    IdeatorReadDto toReadDto(Ideator ideator);

    Set<IdeatorReadDto> toReadDtoSet(Set<Ideator> ideators);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(IdeatorCreateUpdateDto dto, @MappingTarget Ideator ideator);
}
