package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.domain.dto.Project.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { IdeaMapper.class }
)
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "startDate", ignore = true)
    @Mapping(target = "endDate", ignore = true)
    Project toEntity(ProjectCreateUpdateDto dto);

    ProjectReadDto toReadDto(Project project);
    Set<ProjectReadDto> toReadDtoSet(Set<Project> projects);
}

