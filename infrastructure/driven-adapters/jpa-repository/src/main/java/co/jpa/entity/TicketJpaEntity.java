package co.jpa.entity;

import co.model.ticket.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tickets")
public class TicketJpaEntity {

    @Id
    @Column(name = "tick_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tick_title", nullable = false)
    private String title;

    @Column(name = "tick_description", nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "cust_id", nullable = false)
    private UserJpaEntity customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "tick_status", nullable = false)
    private TicketStatus status;

    @Column(name = "tick_createdon", nullable = false)
    private LocalDateTime createdOn;

    @ManyToOne
    @JoinColumn(name = "agen_id")
    private UserJpaEntity agent;

    @Column(name = "tick_assignedon")
    private LocalDateTime assignedOn;

}
