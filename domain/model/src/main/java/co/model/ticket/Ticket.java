package co.model.ticket;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Ticket {

    private final Long id;
    private final String title;
    private final String description;
    private final Long customerId;
    private final TicketStatus status;
    private final LocalDateTime createdOn;
    private final Long agentId;
    private final LocalDateTime assignedOn;

    private Ticket(Long id, String title, String description, Long customerId, TicketStatus status,  LocalDateTime createdOn, Long agentId, LocalDateTime assignedOn) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.customerId = customerId;
        this.status = status;
        this.createdOn = createdOn;
        this.agentId = agentId;
        this.assignedOn = assignedOn;
    }

    public static Ticket create(String title, String description, Long customerId) {
        return new Ticket(null, title, description, customerId, TicketStatus.CREATED, null, null, LocalDateTime.now());
    }

    public static Ticket restore(Long id, String title, String description, Long customerId, TicketStatus status, LocalDateTime createdOn, Long agentId, LocalDateTime assignedOn) {
        return new Ticket(id, title, description, customerId, status, createdOn, agentId, assignedOn);
    }


}
