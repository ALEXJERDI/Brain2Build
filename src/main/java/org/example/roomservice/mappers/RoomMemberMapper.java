package org.example.roomservice.mappers;

import org.example.roomservice.DTO.Roommemberdto.RoomMemberCreateUpdateDto;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberReadDto;
import org.example.roomservice.entity.RoomMember;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMemberMapper {

    // DTO -> Entity
    RoomMember toEntity(RoomMemberCreateUpdateDto dto);

    // Entity -> DTO
    RoomMemberReadDto toReadDto(RoomMember member);
}

