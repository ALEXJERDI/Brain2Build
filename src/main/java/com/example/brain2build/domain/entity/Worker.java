package com.example.brain2build.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("WORKER")
public class Worker extends User {

    @Column(nullable = false)
    private String domaine;      // Backend, Frontend, DevOps, etc.

    @Column(nullable = false)
    private String specialite;   // Java, React, Docker...

    private int experience;      // en années
    private String portfolioUrl; // lien vers son travail


}