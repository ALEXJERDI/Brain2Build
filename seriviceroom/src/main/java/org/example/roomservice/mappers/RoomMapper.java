package org.example.roomservice.mappers;

import org.example.roomservice.DTO.Roomdto.RoomCreateDto;
import org.example.roomservice.DTO.Roomdto.RoomReadDto;
import org.example.roomservice.DTO.Roomdto.RoomUpdateDto;
import org.example.roomservice.entity.Room;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room toEntity(RoomCreateDto dto);  // This should map roomName correctly

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(RoomUpdateDto dto, @MappingTarget Room room);

    RoomReadDto toReadDto(Room room);
}
