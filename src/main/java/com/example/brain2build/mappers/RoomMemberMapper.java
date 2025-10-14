package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.RoomMember;
import com.example.brain2build.domain.dto.RoomMember.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = { WorkerMapper.class }
)
public interface RoomMemberMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "joinedAt", ignore = true)
    @Mapping(target = "room", ignore = true)
    RoomMember toEntity(RoomMemberCreateUpdateDto dto);

    RoomMemberReadDto toReadDto(RoomMember roomMember);
    Set<RoomMemberReadDto> toReadDtoSet(Set<RoomMember> roomMembers);
}

