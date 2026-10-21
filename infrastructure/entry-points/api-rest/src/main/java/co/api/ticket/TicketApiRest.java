package co.api.ticket;

import co.api.config.filter.AuthenticationUser;
import co.api.ticket.mapper.TicketRequestMapper;
import co.api.ticket.mapper.TicketResponseMapper;
import co.api.ticket.request.CreateTicketRequest;
import co.api.ticket.response.TicketResponse;
import co.usecase.ticket.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketApiRest {

    private final CreateTicketUseCase createTicketUseCase;
    private final GetTicketByIdUseCase getTicketByIdUseCase;
    private final StartTicketUseCase startTicketUseCase;
    private final ResolveTicketUseCase resolveTicketUseCase;
    private final CloseTicketUseCase closeTicketUseCase;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@AuthenticationPrincipal AuthenticationUser authenticationUser, @Valid @RequestBody CreateTicketRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TicketResponseMapper
                        .toResponse(createTicketUseCase
                                .execute(TicketRequestMapper
                                        .toCreateTicketCommand(request, authenticationUser.userId()))));
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Long ticketId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(TicketResponseMapper
                        .toResponse(getTicketByIdUseCase
                                .execute(ticketId)));
    }

    @PatchMapping("/{ticketId}/start")
    public ResponseEntity<Void> startTicket(@AuthenticationPrincipal AuthenticationUser authenticationUser, @PathVariable Long ticketId) {
        startTicketUseCase.execute(ticketId, authenticationUser.userId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{ticketId}/resolve")
    public ResponseEntity<Void> resolveTicket(@AuthenticationPrincipal AuthenticationUser authenticationUser, @PathVariable Long ticketId) {
        resolveTicketUseCase.execute(ticketId, authenticationUser.userId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PatchMapping("/{ticketId}/close")
    public ResponseEntity<Void> closeTicket(@AuthenticationPrincipal AuthenticationUser authenticationUser, @PathVariable Long ticketId) {
        closeTicketUseCase.execute(ticketId, authenticationUser.userId());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
