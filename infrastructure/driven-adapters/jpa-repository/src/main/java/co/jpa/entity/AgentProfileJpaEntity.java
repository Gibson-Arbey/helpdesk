package co.jpa.entity;

import co.model.agentprofile.AgentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agentprofiles")
public class AgentProfileJpaEntity {

    @Id
    @Column(name = "agpr_id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private UserJpaEntity user;

    @Enumerated(EnumType.STRING)
    @Column(name = "agpr_status", nullable = false)
    private AgentStatus status;

    @Column(name = "agpr_lastticketclosedat")
    private LocalDateTime lastTicketClosedAt;
}
