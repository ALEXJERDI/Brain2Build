package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Worker;
import com.example.brain2build.domain.dto.Worker.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkerMapper {
    //
    //@Mapping(target = "id", ignore = true)
    //@Mapping(target = "createdAt", ignore = true)
    //@Mapping(target = "updatedAt", ignore = true)
    Worker toEntity(WorkerCreateUpdateDto dto);

    WorkerReadDto toReadDto(Worker worker);

    Set<WorkerReadDto> toReadDtoSet(Set<Worker> workers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(WorkerCreateUpdateDto dto, @MappingTarget Worker worker);
}
