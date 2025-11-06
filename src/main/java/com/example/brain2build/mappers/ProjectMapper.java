package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.domain.dto.Project.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = { RoomMapper.class, IdeaMapper.class }
)public interface ProjectMapper {

    Project toEntity(ProjectCreateUpdateDto dto);

    @Mapping(source = "ideas", target = "ideas")
    @Mapping(source = "room", target = "room")
    ProjectReadDto toReadDto(Project project);

    Set<ProjectReadDto> toReadDtoSet(Set<Project> projects);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(ProjectCreateUpdateDto dto, @MappingTarget Project project);
}

