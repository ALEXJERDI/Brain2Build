package com.example.brain2build.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("IDEATOR")
public class Ideator extends User {

    @Column(length = 255)
    private String bio;

    private int ideaCount = 0; // nombre d’idées proposées

    @OneToMany(mappedBy = "createdBy")
    private List<Idea> ideas;


}
