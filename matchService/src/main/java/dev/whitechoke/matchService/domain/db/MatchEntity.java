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

    @EmbeddedId
    private MatchId id;

    @Column(name = "first_answer")
    private Boolean firstAnswer;

    @Column(name = "second_answer")
    private Boolean secondAnswer;

    public void setIds(Long id1, Long id2) {
        this.id = new MatchId(id1, id2);
    }
}
