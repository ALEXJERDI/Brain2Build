package com.example.brain2build.service.Ideator;

import com.example.brain2build.domain.dto.Idea.IdeaCreateUpdateDto;
import com.example.brain2build.domain.dto.Idea.IdeaReadDto;
import com.example.brain2build.domain.dto.Idea.IdeaUpdateDto;
import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.domain.entity.Ideator;
import com.example.brain2build.mappers.IdeaMapper;
import com.example.brain2build.repository.IdeaRepository;
import com.example.brain2build.repository.IdeatorRepository;
import com.example.brain2build.service.ideator.IdeatorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * ✅ Tests unitaires pour IdeatorServiceImpl
 */
class IdeatorServiceImplTest {

    private IdeatorRepository ideatorRepository;
    private IdeaRepository ideaRepository;
    private IdeaMapper ideaMapper;
    private IdeatorServiceImpl ideatorService;

    @BeforeEach
    void setUp() {
        ideatorRepository = mock(IdeatorRepository.class);
        ideaRepository = mock(IdeaRepository.class);
        ideaMapper = mock(IdeaMapper.class);
        ideatorService = new IdeatorServiceImpl(ideatorRepository, ideaRepository, ideaMapper);
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("proposeIdea : doit créer une idée en statut PENDING")
    void shouldProposeIdea() {
        Ideator ideator = new Ideator();
        ideator.setId(1L);

        Idea idea = new Idea();
        idea.setTitre("Smart Recycling");
        idea.setStatus(Idea.Status.PENDING);

        IdeaCreateUpdateDto dto = new IdeaCreateUpdateDto("Smart Recycling", "An eco app");
        IdeaReadDto dtoRead = new IdeaReadDto(1L, "Smart Recycling", "An eco app", Idea.Status.PENDING, null, null, null);

        when(ideatorRepository.findById(1L)).thenReturn(Optional.of(ideator));
        when(ideaMapper.toEntity(dto)).thenReturn(idea);
        when(ideaRepository.save(any(Idea.class))).thenReturn(idea);
        when(ideaMapper.toReadDto(any(Idea.class))).thenReturn(dtoRead);

        IdeaReadDto result = ideatorService.proposeIdea(1L, dto);

        assertThat(result).isNotNull();
        assertThat(result.getTitre()).isEqualTo("Smart Recycling");
        verify(ideaRepository).save(any(Idea.class));
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("getMyIdeas : doit retourner les idées de l'ideator")
    void shouldReturnMyIdeas() {
        Ideator ideator = new Ideator();
        ideator.setId(2L);

        Idea idea = new Idea();
        idea.setId(5L);
        idea.setTitre("AI for Health");

        when(ideatorRepository.findById(2L)).thenReturn(Optional.of(ideator));
        when(ideaRepository.findByCreatedBy(ideator)).thenReturn(List.of(idea));
        when(ideaMapper.toReadDto(idea)).thenReturn(
                new IdeaReadDto(5L, "AI for Health", "desc", Idea.Status.PENDING, null, null, null)
        );

        List<IdeaReadDto> result = ideatorService.getMyIdeas(2L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitre()).isEqualTo("AI for Health");
        verify(ideaRepository).findByCreatedBy(ideator);
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("updateMyIdea : doit mettre à jour une idée si elle appartient à l'ideator et est PENDING")
    void shouldUpdateMyIdea() {
        Ideator ideator = new Ideator();
        ideator.setId(1L);

        Idea idea = new Idea();
        idea.setId(10L);
        idea.setTitre("Old Idea");
        idea.setStatus(Idea.Status.PENDING);
        idea.setCreatedBy(ideator);

        IdeaUpdateDto dto = new IdeaUpdateDto ("Updated Idea", "Updated Description");
        IdeaReadDto dtoRead = new IdeaReadDto(10L, "Updated Idea", "Updated Description", Idea.Status.PENDING, null, null, null);

        when(ideatorRepository.findById(1L)).thenReturn(Optional.of(ideator));
        when(ideaRepository.findById(10L)).thenReturn(Optional.of(idea));
        when(ideaRepository.save(idea)).thenReturn(idea);
        when(ideaMapper.toReadDto(idea)).thenReturn(dtoRead);

        IdeaReadDto result = ideatorService.updateMyIdea(1L, 10L, dto);

        assertThat(result.getTitre()).isEqualTo("Updated Idea");
        verify(ideaMapper).partialUpdate(dto, idea);
        verify(ideaRepository).save(idea);
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("deleteMyIdea : doit supprimer l'idée si elle appartient à l'ideator et est PENDING")
    void shouldDeleteMyIdea() {
        Ideator ideator = new Ideator();
        ideator.setId(3L);

        Idea idea = new Idea();
        idea.setId(100L);
        idea.setStatus(Idea.Status.PENDING);
        idea.setCreatedBy(ideator);

        when(ideatorRepository.findById(3L)).thenReturn(Optional.of(ideator));
        when(ideaRepository.findById(100L)).thenReturn(Optional.of(idea));

        ideatorService.deleteMyIdea(3L, 100L);

        verify(ideaRepository).delete(idea);
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("getIdeaFeedback : doit retourner le feedback pour l'ideator propriétaire")
    void shouldReturnFeedback() {
        Ideator ideator = new Ideator();
        ideator.setId(5L);

        Idea idea = new Idea();
        idea.setId(200L);
        idea.setStatus(Idea.Status.APPROVED);
        idea.setFeedback("Très bonne idée !");
        idea.setCreatedBy(ideator);

        when(ideatorRepository.findById(5L)).thenReturn(Optional.of(ideator));
        when(ideaRepository.findById(200L)).thenReturn(Optional.of(idea));

        String result = ideatorService.getIdeaFeedback(5L, 200L);

        assertThat(result).contains("APPROVED").contains("Très bonne idée !");
    }

    // --------------------------------------------------------------------
    @Test
    @DisplayName("getIdeaFeedback : doit indiquer que l'idée est en cours d'évaluation si status = PENDING")
    void shouldIndicatePendingFeedback() {
        Ideator ideator = new Ideator();
        ideator.setId(6L);

        Idea idea = new Idea();
        idea.setId(300L);
        idea.setStatus(Idea.Status.PENDING);
        idea.setCreatedBy(ideator);

        when(ideatorRepository.findById(6L)).thenReturn(Optional.of(ideator));
        when(ideaRepository.findById(300L)).thenReturn(Optional.of(idea));

        String result = ideatorService.getIdeaFeedback(6L, 300L);

        assertThat(result).isEqualTo("Your idea is still under review.");
    }
}