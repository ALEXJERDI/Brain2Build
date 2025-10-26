package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.domain.dto.Project.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {

    Project toEntity(ProjectCreateUpdateDto dto);

    ProjectReadDto toReadDto(Project project);

    Set<ProjectReadDto> toReadDtoSet(Set<Project> projects);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(ProjectCreateUpdateDto dto, @MappingTarget Project project);
}
