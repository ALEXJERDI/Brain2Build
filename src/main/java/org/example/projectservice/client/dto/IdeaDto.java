package org.example.projectservice.client.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class IdeaDto implements Serializable {
    Long id;
    String title;   // adapte aux champs exposés par idea-service
    String status;  // ex: "APPROVED", "PENDING", etc.
}
