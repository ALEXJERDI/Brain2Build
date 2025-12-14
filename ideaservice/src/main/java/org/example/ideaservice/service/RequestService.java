package org.example.ideaservice.service;

import org.example.ideaservice.dto.request.RequestCreateDto;
import org.example.ideaservice.dto.request.RequestReadDto;
import org.example.ideaservice.dto.request.RequestUpdateDto;

import java.util.List;

public interface RequestService {
    RequestReadDto createRequest(RequestCreateDto dto);
    RequestReadDto getRequest(Long id);
    List<RequestReadDto> getAllRequests();
    RequestReadDto updateRequest(Long id, RequestUpdateDto dto);
    RequestReadDto activateRequest(Long id);
    RequestReadDto closeRequest(Long id);
}

