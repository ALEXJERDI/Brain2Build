package com.example.brain2build.service.role;

import com.example.brain2build.domain.dto.Role.RoleDto;
import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.mappers.RoleMapper;
import com.example.brain2build.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto createRole(RoleDto dto) {
        Role role = roleMapper.toEntity(dto);
        return roleMapper.toDto(roleRepository.save(role));
    }

    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .toList();
    }
}
