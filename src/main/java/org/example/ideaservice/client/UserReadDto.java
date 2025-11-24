package org.example.ideaservice.client;

import lombok.Value;
import java.io.Serializable;

@Value
public class UserReadDto implements Serializable {
    Long id;        // User ID
    String email;   // User email
    String nom;     // User first name
    String prenom;  // User last name
    String userType;
    public Long getId() {
        return id;
    }
// User type (IDEATOR, WORKER, ADM
// IN)
}
