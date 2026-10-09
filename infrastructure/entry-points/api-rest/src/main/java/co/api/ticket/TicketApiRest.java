package co.api.ticket;

import co.api.ticket.mapper.TicketRequestMapper;
import co.api.ticket.mapper.TicketResponseMapper;
import co.api.ticket.request.CreateTicketRequest;
import co.api.ticket.response.TicketResponse;
import co.usecase.ticket.CreateTicketUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketApiRest {

    private final CreateTicketUseCase createTicketUseCase;

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(@RequestBody CreateTicketRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(TicketResponseMapper
                        .toResponse(createTicketUseCase
                                .execute(TicketRequestMapper
                                        .toCreateTicketCommand(request))));
    }
}
