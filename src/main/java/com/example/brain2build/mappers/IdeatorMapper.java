package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Ideator;
import com.example.brain2build.domain.dto.Ideator.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { RoleMapper.class }
)
public interface IdeatorMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "ideas", ignore = true)
    Ideator toEntity(IdeatorCreateUpdateDto dto);

    @Mapping(target = "roles", source = "roles")
    IdeatorReadDto toReadDto(Ideator ideator);

    Set<IdeatorReadDto> toReadDtoSet(Set<Ideator> ideators);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    Ideator partialUpdate(IdeatorCreateUpdateDto dto, @MappingTarget Ideator ideator);
}


