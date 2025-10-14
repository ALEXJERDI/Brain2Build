package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.dto.Room.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { RoomMemberMapper.class, ProjectMapper.class }
)
public interface RoomMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "members", ignore = true)
    Room toEntity(RoomCreateUpdateDto dto);

    RoomReadDto toReadDto(Room room);
    Set<RoomReadDto> toReadDtoSet(Set<Room> rooms);
}

