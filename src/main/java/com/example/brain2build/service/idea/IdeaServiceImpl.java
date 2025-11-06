package com.example.brain2build.service.idea;

import com.example.brain2build.domain.dto.Idea.*;
import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.mappers.IdeaMapper;
import com.example.brain2build.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final IdeaMapper ideaMapper;

    @Override
    public List<IdeaReadDto> getAllIdeas() {
        return ideaRepository.findAll()
                .stream()
                .map(ideaMapper::toReadDto)
                .toList();
    }

    @Override
    public IdeaReadDto getIdeaById(Long id) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        return ideaMapper.toReadDto(idea);
    }

    @Override
    public List<IdeaReadDto> searchIdeas(String keyword) {
        return ideaRepository.findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword)
                .stream()
                .map(ideaMapper::toReadDto)
                .toList();
    }

    @Override
    public List<IdeaSimpleDto> getApprovedIdeas() {
        return ideaRepository.findByStatus(Idea.Status.APPROVED)
                .stream()
                .map(ideaMapper::toSimpleDto)
                .toList();
    }

    @Override
    public IdeaReadDto approveIdea(Long id, String feedback) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        idea.setStatus(Idea.Status.APPROVED);
        idea.setFeedback(feedback);
        return ideaMapper.toReadDto(ideaRepository.save(idea));
    }

    @Override
    public IdeaReadDto rejectIdea(Long id, String feedback) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        idea.setStatus(Idea.Status.REJECTED);
        idea.setFeedback(feedback);
        return ideaMapper.toReadDto(ideaRepository.save(idea));
    }

    @Override
    public void deleteIdea(Long id) {
        if (!ideaRepository.existsById(id)) {
            throw new RuntimeException("Idea not found");
        }
        ideaRepository.deleteById(id);
    }
}
