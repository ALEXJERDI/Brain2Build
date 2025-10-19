package com.example.brain2build.service.ideator;

import com.example.brain2build.domain.dto.Idea.*;
import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.domain.entity.Ideator;
import com.example.brain2build.mappers.IdeaMapper;
import com.example.brain2build.repository.IdeaRepository;
import com.example.brain2build.repository.IdeatorRepository;
import com.example.brain2build.service.idea.IdeaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IdeatorServiceImpl implements IdeatorService {

    private final IdeatorRepository ideatorRepository;
    private final IdeaRepository ideaRepository;
    private final IdeaMapper ideaMapper;

    @Override
    public IdeaReadDto proposeIdea(Long ideatorId, IdeaCreateUpdateDto dto) {
        Ideator ideator = ideatorRepository.findById(ideatorId)
                .orElseThrow(() -> new RuntimeException("Ideator not found"));
        Idea idea = ideaMapper.toEntity(dto);
        idea.setCreatedBy(ideator);
        idea.setStatus(Idea.Status.PENDING);
        return ideaMapper.toReadDto(ideaRepository.save(idea));
    }

    @Override
    public List<IdeaReadDto> getMyIdeas(Long ideatorId) {
        Ideator ideator = ideatorRepository.findById(ideatorId)
                .orElseThrow(() -> new RuntimeException("Ideator not found"));
        return ideaRepository.findByCreatedBy(ideator)
                .stream()
                .map(ideaMapper::toReadDto)
                .toList();
    }

    @Override
    public IdeaReadDto updateMyIdea(Long ideatorId, Long ideaId, IdeaCreateUpdateDto dto) {
        Ideator ideator = ideatorRepository.findById(ideatorId)
                .orElseThrow(() -> new RuntimeException("Ideator not found"));

        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        if (!idea.getCreatedBy().equals(ideator))
            throw new RuntimeException("You cannot modify this idea");

        if (idea.getStatus() != Idea.Status.PENDING)
            throw new RuntimeException("Idea cannot be modified once reviewed");

        ideaMapper.partialUpdate(dto, idea);
        return ideaMapper.toReadDto(ideaRepository.save(idea));
    }

    @Override
    public void deleteMyIdea(Long ideatorId, Long ideaId) {
        Ideator ideator = ideatorRepository.findById(ideatorId)
                .orElseThrow(() -> new RuntimeException("Ideator not found"));

        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        if (!idea.getCreatedBy().equals(ideator))
            throw new RuntimeException("You cannot delete this idea");

        if (idea.getStatus() != Idea.Status.PENDING)
            throw new RuntimeException("Idea cannot be deleted once reviewed");

        ideaRepository.delete(idea);
    }

    @Override
    public String getIdeaFeedback(Long ideatorId, Long ideaId) {
        Ideator ideator = ideatorRepository.findById(ideatorId)
                .orElseThrow(() -> new RuntimeException("Ideator not found"));

        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        if (!idea.getCreatedBy().equals(ideator))
            throw new RuntimeException("Access denied");

        if (idea.getStatus() == Idea.Status.PENDING)
            return "Your idea is still under review.";

        return idea.getStatus() + ": " + (idea.getFeedback() != null ? idea.getFeedback() : "No feedback");
    }
}
