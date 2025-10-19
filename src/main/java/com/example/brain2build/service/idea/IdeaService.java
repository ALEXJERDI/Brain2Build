package com.example.brain2build.service.idea;

import com.example.brain2build.domain.dto.Idea.*;
import java.util.List;

public interface IdeaService {

    List<IdeaReadDto> getAllIdeas();
    IdeaReadDto getIdeaById(Long id);
    List<IdeaReadDto> searchIdeas(String keyword);
    List<IdeaSimpleDto> getApprovedIdeas();
    IdeaReadDto approveIdea(Long id, String feedback);
    IdeaReadDto rejectIdea(Long id, String feedback);
    void deleteIdea(Long id);
}
