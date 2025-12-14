package org.example.ideaservice.service;

import feign.FeignException;
import org.example.ideaservice.client.UserClient;
import org.example.ideaservice.dto.idea.*;
import org.example.ideaservice.entity.Idea;
import org.example.contracts.events.IdeaApprovedEvent;
import org.example.ideaservice.mapper.IdeaMapper;
import org.example.ideaservice.messaging.EventPublisher;
import org.example.ideaservice.repository.IdeaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final IdeaMapper ideaMapper;
    private final UserClient userClient;
    private final EventPublisher eventPublisher;


    @Override
    public IdeaReadDto createIdea(IdeaCreateDto dto) {

        // Check if user exists
        try {
            userClient.getUserById(dto.getCreatorId());
        } catch (FeignException.NotFound ex) {
            throw new RuntimeException("Creator not found with id " + dto.getCreatorId());
        }

        // Use mapper instead of manual creation
        Idea idea = ideaMapper.fromCreateDto(dto);

        idea.setStatus(Idea.Status.PENDING);
        idea.setCreatedAt(LocalDateTime.now());

        return ideaMapper.toReadDto(ideaRepository.save(idea));
    }

    @Override
    public IdeaReadDto getIdea(Long id) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        return ideaMapper.toReadDto(idea);
    }

    @Override
    public List<IdeaReadDto> getAllIdeas() {
        return ideaRepository.findAll()
                .stream()
                .map(ideaMapper::toReadDto)
                .toList();
    }

    @Override
    public IdeaReadDto updateIdea(Long id, IdeaUpdateDto dto) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        // PARTIAL UPDATE using MapStruct
        ideaMapper.partialUpdate(idea, dto);

        return ideaMapper.toReadDto(ideaRepository.save(idea));
    }

    @Override
    public IdeaReadDto approveIdea(Long id, String feedback) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        idea.setStatus(Idea.Status.APPROVED);
        idea.setFeedback(feedback);

        Idea saved = ideaRepository.save(idea);

        // 🔥 Publish RabbitMQ event
        eventPublisher.publishIdeaApproved(
                IdeaApprovedEvent.builder()
                        .ideaId(saved.getId())
                        .creatorId(saved.getCreatorId())
                        .titre(saved.getTitre())
                        .description(saved.getDescription())
                        .build()
        );

        return ideaMapper.toReadDto(saved);
    }

    @Override
    public IdeaReadDto rejectIdea(Long id, String feedback) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));

        idea.setStatus(Idea.Status.REJECTED);
        idea.setFeedback(feedback);

        return ideaMapper.toReadDto(ideaRepository.save(idea));
    }
}
