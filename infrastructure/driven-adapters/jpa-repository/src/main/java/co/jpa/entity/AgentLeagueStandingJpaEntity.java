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

import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agentleaguestandings", uniqueConstraints = {
        @UniqueConstraint(name = "uq_agentleaguestandings_agent_season", columnNames = {"agpr_id", "lese_id"})
})
public class AgentLeagueStandingJpaEntity {

    @Id
    @Column(name = "agls_id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "agpr_id", nullable = false)
    private AgentProfileJpaEntity agentProfile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "lese_id", nullable = false)
    private LeagueSeasonJpaEntity leagueSeason;

    @Column(name = "agls_points", nullable = false)
    private int points;

    @Column(name = "agls_position")
    private Integer position;
}
