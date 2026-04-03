package dev.whitechoke.userService.domain.db;

import dev.whitechoke.commonLibs.Gender;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profile", indexes = {
        @Index(name = "idx_profile_birthday", columnList = "birthday"),
        @Index(name = "idx_profile_gender", columnList = "gender"),
        @Index(name = "idx_profile_search_gender", columnList = "search_gender")
})
public class ProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "telegram_id",
            unique = true,
            nullable = false
    )
    private Long telegramId;

    @Column(name = "name")
    private String name;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "max_age")
    private Short maxAge;

    @Column(name = "min_age")
    private Short minAge;

    @Column(name = "gender")
    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Column(name = "search_gender")
    @Enumerated(value = EnumType.STRING)
    private Gender searchGender;

    @Column(name = "coordinates", columnDefinition = "geography")
    private Point coordinates;

    @Column(name = "registered_at")
    private Instant registeredAt;

    @Column(name = "bio", length = 500)
    private String bio;

    @Column(name = "is_active")
    private boolean isActive = true;

    @Column(name = "search_radius")
    private Double searchRadius;
}
