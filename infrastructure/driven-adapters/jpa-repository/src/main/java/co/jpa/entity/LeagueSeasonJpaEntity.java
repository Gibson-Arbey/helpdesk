package co.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "leagueseasons", uniqueConstraints = {
        @UniqueConstraint(name = "uq_leagueseasons_league_name", columnNames = {"leag_id", "lese_name"})
})
public class LeagueSeasonJpaEntity {

    @Id
    @Column(name = "lese_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "leag_id", nullable = false)
    private LeagueJpaEntity league;

    @Column(name = "lese_name", nullable = false)
    private String name;

    @Column(name = "lese_startdate", nullable = false)
    private LocalDate startDate;

    @Column(name = "lese_enddate")
    private LocalDate endDate;
}
