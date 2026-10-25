package co.jpa.adapter;

import co.jpa.mapper.TicketJpaMapper;
import co.jpa.repository.TicketJpaRepository;
import co.jpa.repository.AgentProfileJpaRepository;
import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.AgentStatus;
import co.model.agentprofile.exception.AgentNotFoundException;
import co.model.agentprofile.exception.AgentUnavailableException;
import co.model.ticket.TicketStatus;
import co.model.ticket.TicketTransitionResult;
import co.model.ticket.exception.InvalidTicketStatusException;
import co.model.ticket.exception.TicketNotFoundException;
import co.model.ticket.Ticket;
import co.model.ticket.gateways.TicketRepository;
import co.model.exception.InvalidFieldException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TicketJpaAdapter implements TicketRepository {

    private final TicketJpaRepository ticketJpaRepository;
    private final AgentProfileJpaRepository agentProfileJpaRepository;

    @Override
    @Transactional
    public Ticket save(Ticket ticket) {
        return TicketJpaMapper.toDomain(ticketJpaRepository.save(TicketJpaMapper.toEntity(ticket)));
    }

    @Override
    @Transactional
    public TicketTransitionResult saveTransition(Ticket ticket, TicketStatus expectedStatus, AgentProfile updatedAgentProfile) {
        Ticket current = ticketJpaRepository.findByIdForUpdate(ticket.getId())
                .map(TicketJpaMapper::toDomain)
                .orElseThrow(() -> new TicketNotFoundException("Ticket not found with id: " + ticket.getId()));

        if (current.getStatus() != expectedStatus) {
            if (current.getStatus() == ticket.getStatus()) {
                return new TicketTransitionResult(current, false);
            }
            throw new InvalidTicketStatusException(
                    "Ticket with id: " + ticket.getId() + " is no longer in " + expectedStatus + " status"
            );
        }

        if (updatedAgentProfile != null) {
            updateAgentProfileForTransition(ticket, updatedAgentProfile);
        }

        Ticket savedTicket = TicketJpaMapper.toDomain(
                ticketJpaRepository.save(TicketJpaMapper.toEntity(ticket))
        );
        return new TicketTransitionResult(savedTicket, true);
    }

    @Override
    @Transactional(readOnly = true)
    public Ticket findById(Long id) {
        return TicketJpaMapper.toDomain(ticketJpaRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findAll() {
        return ticketJpaRepository.findAll().stream()
                .map(TicketJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findByAgentId(UUID agentId) {
        return ticketJpaRepository.findByAgent_Id(agentId).stream()
                .map(TicketJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Ticket> findByCustomerId(UUID customerId) {
        return ticketJpaRepository.findByCustomer_Id(customerId).stream()
                .map(TicketJpaMapper::toDomain)
                .collect(Collectors.toList());
    }

    private void updateAgentProfileForTransition(Ticket ticket, AgentProfile updatedProfile) {
        if (!Objects.equals(ticket.getAgentId(), updatedProfile.getUserId())) {
            throw new InvalidFieldException("Ticket agent and agent profile user must match");
        }

        var currentProfile = agentProfileJpaRepository.findByIdForUpdate(updatedProfile.getId())
                .orElseThrow(() -> new AgentNotFoundException(
                        "Agent profile not found with id: " + updatedProfile.getId()
                ));

        AgentStatus expectedAgentStatus = switch (updatedProfile.getStatus()) {
            case BUSY -> AgentStatus.AVAILABLE;
            case AVAILABLE -> AgentStatus.BUSY;
            case OFFLINE -> throw new InvalidFieldException("An assigned agent cannot become OFFLINE during a ticket transition");
        };
        if (currentProfile.getStatus() != expectedAgentStatus) {
            if (updatedProfile.getStatus() == AgentStatus.BUSY) {
                throw new AgentUnavailableException("Agent is no longer available for assignment");
            }
            throw new InvalidFieldException("Agent status changed before the ticket transition was saved");
        }

        currentProfile.setStatus(updatedProfile.getStatus());
        currentProfile.setLastTicketClosedAt(updatedProfile.getLastTicketClosedAt());
    }
}
