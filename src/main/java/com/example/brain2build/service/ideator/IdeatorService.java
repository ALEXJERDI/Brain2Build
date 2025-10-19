package com.example.brain2build.service.ideator;

import com.example.brain2build.domain.dto.Idea.*;
import java.util.List;

public interface IdeatorService {

    IdeaReadDto proposeIdea(Long ideatorId, IdeaCreateUpdateDto dto);
    List<IdeaReadDto> getMyIdeas(Long ideatorId);
    IdeaReadDto updateMyIdea(Long ideatorId, Long ideaId, IdeaCreateUpdateDto dto);
    void deleteMyIdea(Long ideatorId, Long ideaId);
    String getIdeaFeedback(Long ideatorId, Long ideaId);
}
