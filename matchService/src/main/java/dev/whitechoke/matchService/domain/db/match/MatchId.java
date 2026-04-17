package dev.whitechoke.matchService.domain.db.match;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class MatchId implements Serializable {

    @Column(name = "first_user_id")
    private Long firstUserTelegramId;

    @Column(name = "second_user_id")
    private Long secondUserTelegramId;

    public MatchId(Long firstId, Long secondId) {
        if (secondId > firstId) {
            this.firstUserTelegramId = firstId;
            this.secondUserTelegramId = secondId;
        } else {
            this.firstUserTelegramId = secondId;
            this.secondUserTelegramId = firstId;
        }
    }

}
