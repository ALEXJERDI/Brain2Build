package org.example.ideaservice.service;

import org.example.ideaservice.dto.idea.IdeaCreateDto;
import org.example.ideaservice.dto.idea.IdeaReadDto;
import org.example.ideaservice.dto.idea.IdeaUpdateDto;

import java.util.List;

public interface IdeaService {
    IdeaReadDto createIdea(IdeaCreateDto dto);
    IdeaReadDto getIdea(Long id);
    List<IdeaReadDto> getAllIdeas();
    IdeaReadDto updateIdea(Long id, IdeaUpdateDto dto);
    IdeaReadDto approveIdea(Long id, String feedback);
    IdeaReadDto rejectIdea(Long id, String feedback);
}
