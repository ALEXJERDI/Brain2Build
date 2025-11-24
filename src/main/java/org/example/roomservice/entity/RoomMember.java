package org.example.roomservice.entity;



import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;
import lombok.*;

import java.time.LocalDateTime;

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

    @Column(name = "is_lead", nullable = false)
    private boolean lead = false;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime joinedAt;

    // ✅ plus d’entité Worker, juste l’id
    @NotNull
    @Column(name = "worker_id", nullable = false)
    private Long workerId;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @NotNull
    private Room room;
}
