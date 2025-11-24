package org.example.ideaservice.Services;

import org.example.ideaservice.dtos.IdeaReadDto;

import java.util.List;

public interface IdeaService {

    List<IdeaReadDto> getAllIdeas();
    IdeaReadDto getIdeaById(Long id);
    List<IdeaReadDto> getApprovedIdeas();
    IdeaReadDto addIdea(Long userId, String title, String description); // Add new method
}
