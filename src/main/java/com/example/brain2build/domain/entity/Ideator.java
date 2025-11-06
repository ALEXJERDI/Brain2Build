package com.example.brain2build.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("IDEATOR")
public class Ideator extends User {

    @Column(length = 255, nullable = true)
    private String bio;

    @Column(nullable = true)
    private int ideaCount = 0; // nombre d’idées proposées


    @OneToMany(mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Idea> ideas = new ArrayList<>();



}
