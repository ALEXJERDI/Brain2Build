package com.example.brain2build.service.Idea;

import com.example.brain2build.domain.dto.Idea.IdeaReadDto;
import com.example.brain2build.domain.dto.Idea.IdeaSimpleDto;
import com.example.brain2build.domain.dto.Ideator.IdeatorSimpleDto;
import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.mappers.IdeaMapper;
import com.example.brain2build.repository.IdeaRepository;
import com.example.brain2build.service.idea.IdeaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires de la classe IdeaServiceImpl
 */
class IdeaServiceImplTest {

    private IdeaRepository ideaRepository;
    private IdeaMapper ideaMapper;
    private IdeaServiceImpl ideaService;

    @BeforeEach
    void setUp() {
        ideaRepository = mock(IdeaRepository.class);
        ideaMapper = mock(IdeaMapper.class);
        ideaService = new IdeaServiceImpl(ideaRepository, ideaMapper);
    }

    @Test
    @DisplayName("getAllIdeas: doit retourner la liste de toutes les idées")
    void shouldReturnAllIdeas() {
        Idea idea1 = new Idea(1L, "Green Energy", "desc1", Idea.Status.PENDING, LocalDateTime.now(), null, null);
        Idea idea2 = new Idea(2L, "Smart Farming", "desc2", Idea.Status.APPROVED, LocalDateTime.now(), null, null);

        when(ideaRepository.findAll()).thenReturn(List.of(idea1, idea2));

        when(ideaMapper.toReadDto(idea1)).thenReturn(
                new IdeaReadDto(1L, "Green Energy", "desc1", Idea.Status.PENDING, LocalDateTime.now(), null, null)
        );
        when(ideaMapper.toReadDto(idea2)).thenReturn(
                new IdeaReadDto(2L, "Smart Farming", "desc2", Idea.Status.APPROVED, LocalDateTime.now(), null, null)
        );

        var result = ideaService.getAllIdeas();

        assertThat(result).hasSize(2);
        assertThat(result.get(0).getTitre()).isEqualTo("Green Energy");
        assertThat(result.get(1).getTitre()).isEqualTo("Smart Farming");

        verify(ideaRepository).findAll();
        verify(ideaMapper, times(2)).toReadDto(any(Idea.class));
    }

    @Test
    @DisplayName("getIdeaById: doit retourner une idée spécifique")
    void shouldReturnIdeaById() {
        Idea idea = new Idea(1L, "AI Assistant", "desc", Idea.Status.APPROVED, LocalDateTime.now(), null, null);

        when(ideaRepository.findById(1L)).thenReturn(Optional.of(idea));
        when(ideaMapper.toReadDto(idea)).thenReturn(
                new IdeaReadDto(1L, "AI Assistant", "desc", Idea.Status.APPROVED, LocalDateTime.now(), null, null)
        );

        var dto = ideaService.getIdeaById(1L);

        assertThat(dto.getTitre()).isEqualTo("AI Assistant");
        verify(ideaRepository).findById(1L);
    }

    @Test
    @DisplayName("searchIdeas: doit retourner les idées contenant un mot-clé")
    void shouldSearchIdeas() {
        Idea idea = new Idea(3L, "AI in Education", "desc", Idea.Status.PENDING, LocalDateTime.now(), null, null);

        when(ideaRepository.findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase("AI", "AI"))
                .thenReturn(List.of(idea));
        when(ideaMapper.toReadDto(idea)).thenReturn(
                new IdeaReadDto(3L, "AI in Education", "desc", Idea.Status.PENDING, LocalDateTime.now(), null, null)
        );

        var result = ideaService.searchIdeas("AI");

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitre()).isEqualTo("AI in Education");
    }

    @Test
    @DisplayName("getApprovedIdeas: doit retourner uniquement les idées approuvées")
    void shouldReturnApprovedIdeas() {
        Idea idea = new Idea(1L, "Approved Idea", "desc", Idea.Status.APPROVED, LocalDateTime.now(), null, null);

        when(ideaRepository.findByStatus(Idea.Status.APPROVED)).thenReturn(List.of(idea));
        when(ideaMapper.toSimpleDto(idea)).thenReturn(new IdeaSimpleDto(1L, "Approved Idea", "APPROVED"));

        var result = ideaService.getApprovedIdeas();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getTitle()).isEqualTo("Approved Idea");
        assertThat(result.get(0).getStatus()).isEqualTo("APPROVED");
        verify(ideaRepository).findByStatus(Idea.Status.APPROVED);
    }

    @Test
    @DisplayName("approveIdea: doit passer une idée en statut APPROVED avec feedback")
    void shouldApproveIdea() {
        Idea idea = new Idea(10L, "Eco App", "desc", Idea.Status.PENDING, LocalDateTime.now(), null, null);

        when(ideaRepository.findById(10L)).thenReturn(Optional.of(idea));
        when(ideaRepository.save(any(Idea.class))).thenReturn(idea);
        when(ideaMapper.toReadDto(idea)).thenReturn(
                new IdeaReadDto(10L, "Eco App", "desc", Idea.Status.APPROVED, LocalDateTime.now(), null, "Bon travail")
        );

        var dto = ideaService.approveIdea(10L, "Bon travail");

        assertThat(dto.getStatus()).isEqualTo(Idea.Status.APPROVED);
        assertThat(dto.getFeedback()).isEqualTo("Bon travail");

        verify(ideaRepository).save(idea);
    }

    @Test
    @DisplayName("rejectIdea: doit passer une idée en statut REJECTED avec feedback")
    void shouldRejectIdea() {
        Idea idea = new Idea(20L, "Bad Idea", "desc", Idea.Status.PENDING, LocalDateTime.now(), null, null);

        when(ideaRepository.findById(20L)).thenReturn(Optional.of(idea));
        when(ideaRepository.save(any(Idea.class))).thenReturn(idea);
        when(ideaMapper.toReadDto(idea)).thenReturn(
                new IdeaReadDto(20L, "Bad Idea", "desc", Idea.Status.REJECTED, LocalDateTime.now(), null, "Non viable")
        );

        var dto = ideaService.rejectIdea(20L, "Non viable");

        assertThat(dto.getStatus()).isEqualTo(Idea.Status.REJECTED);
        assertThat(dto.getFeedback()).isEqualTo("Non viable");

        verify(ideaRepository).save(idea);
    }

    @Test
    @DisplayName("deleteIdea: doit supprimer une idée existante")
    void shouldDeleteIdea() {
        when(ideaRepository.existsById(99L)).thenReturn(true);

        ideaService.deleteIdea(99L);

        verify(ideaRepository).deleteById(99L);
    }

    @Test
    @DisplayName("deleteIdea: doit lever une exception si l'idée n'existe pas")
    void shouldThrowWhenDeletingUnknownIdea() {
        when(ideaRepository.existsById(123L)).thenReturn(false);

        assertThatThrownBy(() -> ideaService.deleteIdea(123L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Idea not found");

        verify(ideaRepository, never()).deleteById(any());
    }
}