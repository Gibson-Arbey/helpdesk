package co.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "leagues")
public class LeagueJpaEntity {

    @Id
    @Column(name = "leag_id")
    private UUID id;

    @Column(name = "leag_name", nullable = false, unique = true)
    private String name;

    @Column(name = "leag_description")
    private String description;

    @Column(name = "leag_maximunnumberofopentickets", nullable = false)
    private Integer maximumNumberOfOpenTickets;

    @Column(name = "leag_maximumnumberofpoints", nullable = false)
    private Integer maximumNumberOfPoints;

    @Column(name = "leag_minimumnumberofpoints", nullable = false)
    private Integer minimumNumberOfPoints;
}
