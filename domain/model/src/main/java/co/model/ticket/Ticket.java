package co.model.ticket;

import co.model.exception.InvalidFieldException;
import lombok.Getter;

import java.net.PortUnreachableException;
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
        if (title == null || title.isEmpty()) {
            throw new InvalidFieldException("Title cannot be null or empty");
        }
        if (description == null || description.isEmpty()) {
            throw new InvalidFieldException("Description cannot be null or empty");
        }
        if (customerId == null) {
            throw new InvalidFieldException("CustomerId cannot be null");
        }
        if (status == null) {
            throw new InvalidFieldException("Status cannot be null");
        }
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
        return new Ticket(null, title, description, customerId, TicketStatus.CREATED, LocalDateTime.now(), null, null);
    }

    public static Ticket restore(Long id, String title, String description, Long customerId, TicketStatus status, LocalDateTime createdOn, Long agentId, LocalDateTime assignedOn) {
        return new Ticket(id, title, description, customerId, status, createdOn, agentId, assignedOn);
    }

    public Ticket assignAgent(Long agentId) {

        if(this.status != TicketStatus.CREATED) {
            throw new InvalidFieldException("Ticket is not in CREATED status and cannot be assigned.");
        }

        if (agentId == null) {
            throw new InvalidFieldException("AgentId cannot be null.");
        }

        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.ASSIGNED, this.createdOn, agentId, LocalDateTime.now());
    }

    public Ticket start() {

        if(this.status != TicketStatus.ASSIGNED) {
            throw new InvalidFieldException("Ticket is not in ASSIGNED status and cannot be started.");
        }

        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.IN_PROGRESS, this.createdOn, this.agentId, this.assignedOn);
    }

    public Ticket resolve() {

        if(this.status != TicketStatus.IN_PROGRESS) {
            throw new InvalidFieldException("Ticket is not in IN_PROGRESS status and cannot be resolved.");
        }

        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.RESOLVED, this.createdOn, this.agentId, this.assignedOn);
    }

    public Ticket close() {

        if(this.status != TicketStatus.RESOLVED) {
            throw new InvalidFieldException("Ticket is not in RESOLVED status and cannot be closed.");
        }

        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.CLOSED, this.createdOn, this.agentId, this.assignedOn);
    }


}
