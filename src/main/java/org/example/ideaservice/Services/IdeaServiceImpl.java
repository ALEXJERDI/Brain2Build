package org.example.ideaservice.Services;

import org.example.ideaservice.client.UserClient;
import org.example.ideaservice.client.UserReadDto;
import org.example.ideaservice.dtos.IdeaReadDto;
import org.example.ideaservice.entities.Idea;
import org.example.ideaservice.repositories.IdeaRepository;
import org.example.ideaservice.Mapper.IdeaMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class IdeaServiceImpl implements IdeaService {

    private final IdeaRepository ideaRepository;
    private final IdeaMapper ideaMapper;
    private final UserClient userClient;  // Inject the UserClient to fetch user data

    public IdeaServiceImpl(IdeaRepository ideaRepository, IdeaMapper ideaMapper, UserClient userClient) {
        this.ideaRepository = ideaRepository;
        this.ideaMapper = ideaMapper;
        this.userClient = userClient;
    }

    @Override
    public List<IdeaReadDto> getAllIdeas() {
        return ideaRepository.findAll()
                .stream()
                .map(ideaMapper::toReadDto)  // Mapping to IdeaReadDto
                .collect(Collectors.toList());
    }

    @Override
    public IdeaReadDto getIdeaById(Long id) {
        Idea idea = ideaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Idea not found"));
        return ideaMapper.toReadDto(idea);  // Mapping to IdeaReadDto
    }

    @Override
    public List<IdeaReadDto> getApprovedIdeas() {
        return ideaRepository.findByStatus(Idea.Status.APPROVED)
                .stream()
                .map(ideaMapper::toReadDto)  // Mapping to IdeaReadDto
                .collect(Collectors.toList());
    }

    @Override
    public IdeaReadDto addIdea(Long userId, String title, String description) {
        // Fetch the user from UserService using Feign client
        UserReadDto userDto = userClient.getUserById(userId); // Get user data

        // Create a new Idea entity
        Idea newIdea = new Idea();
        newIdea.setTitre(title);                   // Setting title of the idea
        newIdea.setDescription(description);       // Setting description of the idea
        newIdea.setCreatedById(userDto.getId());   // Setting the user ID who created the idea
        newIdea.setStatus(Idea.Status.PENDING);    // Default status for new idea
        newIdea.setCreatedAt(LocalDateTime.now()); // Set current timestamp for the idea

        // Save the new idea to the database
        Idea savedIdea = ideaRepository.save(newIdea);

        // Return the saved idea as DTO
        return ideaMapper.toReadDto(savedIdea);
    }


}
