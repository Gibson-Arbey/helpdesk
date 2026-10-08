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
    private final Long agentId;
    private final LocalDateTime assignedOn;
    private final LocalDateTime createdOn;

    private Ticket(Long id, String title, String description, Long customerId, TicketStatus status, Long agentId, LocalDateTime assignedOn, LocalDateTime createdOn) {

        this.id = id;
        this.title = title;
        this.description = description;
        this.customerId = customerId;
        this.status = status;
        this.agentId = agentId;
        this.assignedOn = assignedOn;
        this.createdOn = createdOn;
    }

    public static Ticket create(String title, String description, Long customerId, TicketStatus status) {
        return new Ticket(null, title, description, customerId, status, null, null, LocalDateTime.now());
    }

    public static Ticket restore(Long id, String title, String description, Long customerId, TicketStatus status, Long agentId, LocalDateTime assignedOn, LocalDateTime createdOn) {
        return new Ticket(id, title, description, customerId, status, agentId, assignedOn, createdOn);
    }


}
