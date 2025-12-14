package org.example.ideaservice.service;

import org.example.ideaservice.client.UserClient;
import org.example.ideaservice.dto.request.RequestCreateDto;
import org.example.ideaservice.dto.request.RequestReadDto;
import org.example.ideaservice.dto.request.RequestUpdateDto;
import org.example.ideaservice.entity.Request;
import org.example.contracts.events.RequestActivatedEvent;
import org.example.ideaservice.mapper.RequestMapper;
import org.example.ideaservice.messaging.EventPublisher;
import org.example.ideaservice.repository.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final UserClient userClient;
    private final EventPublisher eventPublisher;


    // ---------------------------------------------------
    // CREATE REQUEST
    // ---------------------------------------------------
    @Override
    public RequestReadDto createRequest(RequestCreateDto dto) {

        // Verify user exists
        userClient.getUserById(dto.getCreatorId());

        // Use MapStruct mapper
        Request request = requestMapper.fromCreateDto(dto);

        request.setStatus(Request.Status.PENDING);
        request.setCreatedAt(LocalDateTime.now());

        return requestMapper.toReadDto(requestRepository.save(request));
    }

    // ---------------------------------------------------
    // GET REQUEST
    // ---------------------------------------------------
    @Override
    public RequestReadDto getRequest(Long id) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));
        return requestMapper.toReadDto(request);
    }

    // ---------------------------------------------------
    // LIST REQUESTS
    // ---------------------------------------------------
    @Override
    public List<RequestReadDto> getAllRequests() {
        return requestRepository.findAll()
                .stream()
                .map(requestMapper::toReadDto)
                .toList();
    }

    // ---------------------------------------------------
    // UPDATE REQUEST (PARTIAL UPDATE)
    // ---------------------------------------------------
    @Override
    public RequestReadDto updateRequest(Long id, RequestUpdateDto dto) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        // Partial update via MapStruct
        requestMapper.partialUpdate(request, dto);

        return requestMapper.toReadDto(requestRepository.save(request));
    }

    // ---------------------------------------------------
    // ACTIVATE REQUEST (becomes project later)
    // ---------------------------------------------------
    @Override
    public RequestReadDto activateRequest(Long id) {

        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(Request.Status.ACTIVE);

        Request saved = requestRepository.save(request);

        // 🔥 Publish RabbitMQ event
        eventPublisher.publishRequestActivated(
                RequestActivatedEvent.builder()
                        .requestId(saved.getId())
                        .creatorId(saved.getCreatorId())
                        .titre(saved.getTitre())
                        .description(saved.getDescription())
                        .budget(saved.getBudget())
                        .build()
        );

        return requestMapper.toReadDto(saved);
    }

    // ---------------------------------------------------
    // CLOSE REQUEST
    // ---------------------------------------------------
    @Override
    public RequestReadDto closeRequest(Long id) {

        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Request not found"));

        request.setStatus(Request.Status.CLOSED);

        return requestMapper.toReadDto(requestRepository.save(request));
    }
}
