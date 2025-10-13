package com.example.brain2build.domain.dto.Project;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Project}
 */
@Value
public class ProjectSimpleDto implements Serializable {
    Long id;
    String projectName;
    String status;

}