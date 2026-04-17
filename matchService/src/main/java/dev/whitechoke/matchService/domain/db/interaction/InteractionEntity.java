package dev.whitechoke.matchService.domain.db.interaction;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "interaction")
public class InteractionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "interacted_user_id")
    private Long interactedUserId;

    public InteractionEntity(Long userId, Long interactedUserId) {
        this.userId = userId;
        this.interactedUserId = interactedUserId;
    }
}
