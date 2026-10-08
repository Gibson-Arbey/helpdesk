package co.api.ticket;

import co.usecase.ticket.CreateTicketUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketApiRest {

    private final CreateTicketUseCase createTicketUseCase;

    public ResponseEntity<?> createTicket(TicketRequest ticketRequest) {
        // Call the use case to create a ticket
        var ticket = createTicketUseCase.createTicket(ticketRequest.getTitle(), ticketRequest.getDescription(), ticketRequest.getCustomerId());
        return ResponseEntity.ok(ticket);
    }
}
