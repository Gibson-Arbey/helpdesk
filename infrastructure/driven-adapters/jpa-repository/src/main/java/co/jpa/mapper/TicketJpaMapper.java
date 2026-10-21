package co.jpa.mapper;

import co.jpa.entity.TicketJpaEntity;
import co.jpa.entity.UserJpaEntity;
import co.model.ticket.Ticket;

public class TicketJpaMapper {

    public static Ticket toDomain(TicketJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return Ticket.restore(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getCustomer().getId(),
                entity.getStatus(),
                entity.getCreatedOn(),
                entity.getAgent().getId(),
                entity.getAssignedOn()
        );
    }

    public static TicketJpaEntity toEntity(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        return TicketJpaEntity.builder()
                .id(ticket.getId())
                .title(ticket.getTitle())
                .description(ticket.getDescription())
                .customer(ticket.getCustomerId() != null ? UserJpaEntity.builder().id(ticket.getCustomerId()).build() : null)
                .status(ticket.getStatus())
                .createdOn(ticket.getCreatedOn())
                .agent(ticket.getAgentId() != null ? UserJpaEntity.builder().id(ticket.getAgentId()).build() : null)
                .assignedOn(ticket.getAssignedOn())
                .build();
    }
}
