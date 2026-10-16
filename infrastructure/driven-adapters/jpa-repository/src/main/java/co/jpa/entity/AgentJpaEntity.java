package co.jpa.entity;

import co.model.agentprofile.AgentStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "agents")
public class AgentJpaEntity {

    @Id
    @Column(name = "agen_id")
    private Long id;

    @Column(name = "agen_name")
    private String name;

    @Column(name = "agen_email")
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "agen_status")
    private AgentStatus status;
}
