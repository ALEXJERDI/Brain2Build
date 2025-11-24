package org.example.ideaservice.Mapper;


import org.example.ideaservice.dtos.IdeaCreateUpdateDto;
import org.example.ideaservice.dtos.IdeaReadDto;
import org.example.ideaservice.entities.Idea;
 // Import your Status enum here
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")  // Enable Spring Component
public interface IdeaMapper {

    // Convert IdeaCreateUpdateDto to Idea entity
    Idea toEntity(IdeaCreateUpdateDto dto);

    // Convert Idea entity to IdeaReadDto
    IdeaReadDto toReadDto(Idea idea);

    // Custom mapping for Idea.Status to Status (in DTO)
    default Idea.Status map(Idea.Status status) {
        if (status == null) {
            return null;
        }
        return Idea.Status.valueOf(status.name());  // Converts Idea.Status to Status enum in DTO
    }

    // Partial update of Idea entity with Ignore strategy for null values in the DTO
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdate(IdeaCreateUpdateDto dto, @MappingTarget Idea idea);
}
