package co.model.ticket;

import co.model.exception.InvalidFieldException;
import co.model.ticket.exception.InvalidTicketStatusException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Ticket {

    private final Long id;
    private final String title;
    private final String description;
    private final UUID customerId;
    private final TicketStatus status;
    private final LocalDateTime createdOn;
    private final UUID agentId;
    private final LocalDateTime assignedOn;

    private Ticket(Long id, String title, String description, UUID customerId, TicketStatus status, LocalDateTime createdOn, UUID agentId, LocalDateTime assignedOn) {
        if (title == null || title.isBlank()) {
            throw new InvalidFieldException("Title cannot be blank");
        }
        if (description == null || description.isBlank()) {
            throw new InvalidFieldException("Description cannot be blank");
        }
        if (customerId == null) {
            throw new InvalidFieldException("CustomerId cannot be null");
        }
        if (status == null) {
            throw new InvalidFieldException("Status cannot be null");
        }
        if (createdOn == null) {
            throw new InvalidFieldException("CreatedOn cannot be null");
        }
        if ((agentId == null) != (assignedOn == null)) {
            throw new InvalidFieldException("AgentId and AssignedOn must either both be present or both be absent");
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

    public static Ticket create(String title, String description, UUID customerId) {
        return new Ticket(null, title, description, customerId, TicketStatus.CREATED, LocalDateTime.now(), null, null);
    }

    public static Ticket restore(Long id, String title, String description, UUID customerId, TicketStatus status, LocalDateTime createdOn, UUID agentId, LocalDateTime assignedOn) {
        return new Ticket(id, title, description, customerId, status, createdOn, agentId, assignedOn);
    }

    public Ticket assignAgent(UUID agentId) {
        if(this.status != TicketStatus.CREATED) {
            throw new InvalidTicketStatusException("Ticket is not in CREATED status and cannot be assigned.");
        }

        if (agentId == null) {
            throw new InvalidFieldException("AgentId cannot be null.");
        }

        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.ASSIGNED, this.createdOn, agentId, LocalDateTime.now());
    }

    public Ticket start() {
        if(this.status != TicketStatus.ASSIGNED) {
            throw new InvalidTicketStatusException("Ticket is not in ASSIGNED status and cannot be started.");
        }

        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.IN_PROGRESS, this.createdOn, this.agentId, this.assignedOn);
    }

    public Ticket resolve() {
        if(this.status != TicketStatus.IN_PROGRESS) {
            throw new InvalidTicketStatusException("Ticket is not in IN_PROGRESS status and cannot be resolved.");
        }

        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.RESOLVED, this.createdOn, this.agentId, this.assignedOn);
    }

    public Ticket close() {
        if(this.status != TicketStatus.RESOLVED) {
            throw new InvalidTicketStatusException("Ticket is not in RESOLVED status and cannot be closed.");
        }

        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.CLOSED, this.createdOn, this.agentId, this.assignedOn);
    }

    public Ticket cancel() {
        if (this.status != TicketStatus.CREATED && this.status != TicketStatus.ASSIGNED) {
            throw new InvalidTicketStatusException("Only CREATED or ASSIGNED tickets can be cancelled.");
        }
        return new Ticket(this.id, this.title, this.description, this.customerId, TicketStatus.CANCELLED, this.createdOn, this.agentId, this.assignedOn);
    }

}
