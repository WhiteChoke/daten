package dev.whitechoke.matchService.domain.db;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "match")
public class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "first_user")
    private Long firstUserTelegramId;

    @Column(name = "second_user")
    private Long secondUserTelegramId;

    @Column(name = "first_answer")
    private Boolean firstAnswer;

    @Column(name = "second_answer")
    private Boolean secondAnswer;
}
