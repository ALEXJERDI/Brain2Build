package com.example.brain2build.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "room_members")
public class RoomMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_in_room", length = 50)
    private String roleInRoom;

    @Column(name = "is_lead")
    private boolean lead = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    @ManyToOne
    @JoinColumn(name = "worker_id")
    @NotNull
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull
    private Room room;







}
