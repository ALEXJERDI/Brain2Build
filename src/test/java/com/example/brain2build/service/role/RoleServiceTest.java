package com.example.brain2build.service.role;

import com.example.brain2build.domain.dto.Role.RoleDto;
import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.mappers.RoleMapper;
import com.example.brain2build.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class RoleServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private RoleMapper roleMapper;

    @InjectMocks
    private RoleServiceImpl roleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ============================================================
    // ✅ TEST 1 : Create Role
    // ============================================================
    @Test
    @DisplayName("Should create a role successfully")
    void shouldCreateRole() {
        // Prepare the RoleDto
        RoleDto roleDto = new RoleDto(1L, "ROLE_ADMIN");

        // Prepare the Role entity to be returned
        Role role = new Role();
        role.setNom("ROLE_ADMIN");

        // Mock the behavior of RoleMapper and RoleRepository
        when(roleMapper.toEntity(roleDto)).thenReturn(role);
        when(roleRepository.save(role)).thenReturn(role);
        when(roleMapper.toDto(role)).thenReturn(roleDto);

        // Call the service method
        RoleDto result = roleService.createRole(roleDto);

        // Validate that everything works as expected
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("ROLE_ADMIN");

        // Verify that the mapper and repository were called
        verify(roleMapper, times(1)).toEntity(roleDto);
        verify(roleRepository, times(1)).save(role);
        verify(roleMapper, times(1)).toDto(role);
    }

    // ============================================================
    // ✅ TEST 2 : Get All Roles
    // ============================================================
    @Test
    @DisplayName("Should return all roles successfully")
    void shouldGetAllRoles() {
        // Prepare mock data
        Role role1 = new Role();
        role1.setNom("ROLE_ADMIN");

        Role role2 = new Role();
        role2.setNom("ROLE_USER");

        // Prepare RoleDto for each Role
        RoleDto roleDto1 = new RoleDto(1L, "ROLE_ADMIN");
        RoleDto roleDto2 = new RoleDto(2L, "ROLE_USER");

        // Mock the behavior of RoleRepository and RoleMapper
        when(roleRepository.findAll()).thenReturn(List.of(role1, role2));
        when(roleMapper.toDto(role1)).thenReturn(roleDto1);
        when(roleMapper.toDto(role2)).thenReturn(roleDto2);

        // Call the service method
        List<RoleDto> result = roleService.getAllRoles();

        // Validate the results
        assertThat(result).hasSize(2);
        assertThat(result).extracting(RoleDto::getNom).containsExactlyInAnyOrder("ROLE_ADMIN", "ROLE_USER");

        // Verify that the repository and mapper were called
        verify(roleRepository, times(1)).findAll();
        verify(roleMapper, times(1)).toDto(role1);
        verify(roleMapper, times(1)).toDto(role2);
    }
}
