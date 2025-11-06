package com.example.brain2build.mappers;

import com.example.brain2build.domain.entity.RoomMember;
import com.example.brain2build.domain.dto.RoomMember.*;
import org.mapstruct.*;
import java.util.Set;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMemberMapper {

    RoomMember toEntity(RoomMemberCreateUpdateDto dto);
    @Mapping(source = "lead", target = "lead")
    RoomMemberReadDto toReadDto(RoomMember roomMember);

    Set<RoomMemberReadDto> toReadDtoSet(Set<RoomMember> roomMembers);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(RoomMemberCreateUpdateDto dto, @MappingTarget RoomMember roomMember);
}
