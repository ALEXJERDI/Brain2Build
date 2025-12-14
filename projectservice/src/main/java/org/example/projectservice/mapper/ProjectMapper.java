package org.example.projectservice.mapper;

import org.example.projectservice.dto.Project.ProjectCreateUpdateDto;
import org.example.projectservice.dto.Project.ProjectReadDto;
import org.example.projectservice.dto.Project.ProjectUpdateDto;
import org.example.projectservice.entity.Project;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ProjectMapper {

    // ---- CREATE ----
    Project toEntity(ProjectCreateUpdateDto dto);

    // ---- READ ----
    ProjectReadDto toReadDto(Project project);

    List<ProjectReadDto> toReadDtoList(List<Project> projects);

    // ---- UPDATE ----
    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    void updateProjectFromDto(ProjectUpdateDto dto, @MappingTarget Project project);
}

