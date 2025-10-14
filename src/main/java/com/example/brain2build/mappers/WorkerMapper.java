package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Worker;
import com.example.brain2build.domain.dto.Worker.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { RoleMapper.class }
)
public interface WorkerMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "roles", ignore = true)
    Worker toEntity(WorkerCreateUpdateDto dto);

    @Mapping(target = "roles", source = "roles")
    WorkerReadDto toReadDto(Worker worker);

    Set<WorkerReadDto> toReadDtoSet(Set<Worker> workers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "roles", ignore = true)
    Worker partialUpdate(WorkerCreateUpdateDto dto, @MappingTarget Worker worker);
}

