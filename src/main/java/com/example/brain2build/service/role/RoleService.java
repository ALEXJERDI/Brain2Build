package com.example.brain2build.service.role;

import com.example.brain2build.domain.dto.Role.RoleDto;
import java.util.List;

public interface RoleService {

    RoleDto createRole(RoleDto dto);

    List<RoleDto> getAllRoles();
}
