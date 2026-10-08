package co.jpa.adapter;

import co.jpa.mapper.TicketJpaMapper;
import co.jpa.repository.TicketJpaRepository;
import co.model.ticket.Ticket;
import co.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TicketJpaAdapter implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;

    @Override
    public Ticket save(Ticket ticket) {
        return TicketJpaMapper.toDomain(ticketJpaRepository.save(TicketJpaMapper.toEntity(ticket)));
    }

    @Override
    public Ticket findById(Long id) {
        return TicketJpaMapper.toDomain(ticketJpaRepository.findById(id).orElse(null));
    }
}
