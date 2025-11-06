package com.example.brain2build.domain.dto.Project;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Value
public class ProjectUpdateDto implements Serializable {
    // optional – only set if you want to change it
     String projectName;

    // optional – only set if you want to change linked ideas
     Set<Long> ideaIds;
}
