package co.jpa.mapper;

import co.jpa.entity.TicketJpaEntity;
import co.jpa.entity.UserJpaEntity;
import co.model.ticket.TicketStatus;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TicketJpaMapperTest {

    @Test
    void mapsUnassignedTicketWithUuidCustomerAndNullAgent() {
        UUID customerId = UUID.randomUUID();
        TicketJpaEntity entity = TicketJpaEntity.builder()
                .id(17L)
                .title("Login")
                .description("Cannot sign in")
                .customer(UserJpaEntity.builder().id(customerId).build())
                .status(TicketStatus.CREATED)
                .createdOn(LocalDateTime.now())
                .agent(null)
                .assignedOn(null)
                .build();

        var ticket = TicketJpaMapper.toDomain(entity);

        assertEquals(17L, ticket.getId());
        assertEquals(customerId, ticket.getCustomerId());
        assertEquals(TicketStatus.CREATED, ticket.getStatus());
        assertNull(ticket.getAgentId());
        assertNull(ticket.getAssignedOn());
    }

    @Test
    void mapsNewTicketWithoutAssigningAnAgent() {
        UUID customerId = UUID.randomUUID();
        var ticket = co.model.ticket.Ticket.create("Login", "Cannot sign in", customerId);

        TicketJpaEntity entity = TicketJpaMapper.toEntity(ticket);

        assertEquals(customerId, entity.getCustomer().getId());
        assertNull(entity.getAgent());
        assertNull(entity.getAssignedOn());
    }
}
