package co.api.ticket;

import co.api.config.filter.AuthenticationUser;
import co.api.ticket.mapper.TicketRequestMapper;
import co.api.ticket.mapper.TicketResponseMapper;
import co.api.ticket.request.CreateTicketRequest;
import co.api.ticket.response.TicketResponse;
import co.usecase.ticket.CancelTicketUseCase;
import co.usecase.ticket.CloseTicketUseCase;
import co.usecase.ticket.CreateTicketUseCase;
import co.usecase.ticket.GetTicketByIdUseCase;
import co.usecase.ticket.GetTicketsByAgentUseCase;
import co.usecase.ticket.GetTicketsByCustomerUseCase;
import co.usecase.ticket.GetTicketsUseCase;
import co.usecase.ticket.ResolveTicketUseCase;
import co.usecase.ticket.StartTicketUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketApiRest {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetTicketByIdUseCase getTicketByIdUseCase;
    private final GetTicketsUseCase getTicketsUseCase;
    private final GetTicketsByAgentUseCase getTicketsByAgentUseCase;
    private final GetTicketsByCustomerUseCase getTicketsByCustomerUseCase;
    private final StartTicketUseCase startTicketUseCase;
    private final ResolveTicketUseCase resolveTicketUseCase;
    private final CloseTicketUseCase closeTicketUseCase;
    private final CancelTicketUseCase cancelTicketUseCase;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @Valid @RequestBody CreateTicketRequest request
    ) {
        var command = TicketRequestMapper.toCreateTicketCommand(request, authenticationUser.userId());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(TicketResponseMapper.toResponse(createTicketUseCase.execute(command)));
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> getTickets() {
        return ResponseEntity.ok(TicketResponseMapper.toResponses(getTicketsUseCase.execute()));
    }

    @GetMapping("/agent/{agentId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByAgent(@PathVariable UUID agentId) {
        return ResponseEntity.ok(TicketResponseMapper.toResponses(getTicketsByAgentUseCase.execute(agentId)));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<TicketResponse>> getTicketsByCustomer(@PathVariable UUID customerId) {
        return ResponseEntity.ok(TicketResponseMapper.toResponses(getTicketsByCustomerUseCase.execute(customerId)));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long ticketId) {
        return ResponseEntity.ok(TicketResponseMapper.toResponse(getTicketByIdUseCase.execute(ticketId)));
    }

    @PatchMapping("/{ticketId}/start")
    public ResponseEntity<Void> startTicket(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @PathVariable Long ticketId
    ) {
        startTicketUseCase.execute(ticketId, authenticationUser.userId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ticketId}/resolve")
    public ResponseEntity<Void> resolveTicket(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @PathVariable Long ticketId
    ) {
        resolveTicketUseCase.execute(ticketId, authenticationUser.userId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ticketId}/close")
    public ResponseEntity<Void> closeTicket(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @PathVariable Long ticketId
    ) {
        closeTicketUseCase.execute(ticketId, authenticationUser.userId());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{ticketId}/cancel")
    public ResponseEntity<Void> cancelTicket(
            @AuthenticationPrincipal AuthenticationUser authenticationUser,
            @PathVariable Long ticketId
    ) {
        cancelTicketUseCase.execute(ticketId, authenticationUser.userId());
        return ResponseEntity.noContent().build();
    }
}
