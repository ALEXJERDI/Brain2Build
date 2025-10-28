package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.dto.Room.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room toEntity(RoomCreateUpdateDto dto);

    RoomReadDto toReadDto(Room room);

    Set<RoomReadDto> toReadDtoSet(Set<Room> rooms);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(RoomCreateUpdateDto dto, @MappingTarget Room room);
}
