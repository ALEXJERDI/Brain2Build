package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.dto.Room.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    // ✅ For creation
    Room toEntity(RoomCreateDto dto);

    // ✅ For updates (partial)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(RoomUpdateDto dto, @MappingTarget Room room);

    // ✅ For reading
    RoomReadDto toReadDto(Room room);

    Set<RoomReadDto> toReadDtoSet(Set<Room> rooms);
}
