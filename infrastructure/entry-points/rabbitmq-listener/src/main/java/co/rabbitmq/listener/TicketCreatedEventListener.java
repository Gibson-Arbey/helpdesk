package co.rabbitmq.listener;

import co.model.ticketevent.TicketCreatedEvent;
import co.usecase.ticket.AssignTicketUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketCreatedEventListener {

    private final AssignTicketUseCase assignTicketUseCase;

    @RabbitListener(queues = "ticket-assignment-queue")
    public void handleTicketCreatedEvent(TicketCreatedEvent event) {
        log.info("Evento recibido en ticket-assignment-queue para ticket: {}", event.id());
        assignTicketUseCase.execute(event.id());
    }

    @RabbitListener(queues = "ticket-notification-queue")
    public void handleTicketNotificationEvent(TicketCreatedEvent event) {
        log.info("Evento recibido en ticket-notification-queue para ticket: {}", event.id());
    }

    @RabbitListener(queues = "ticket-audit-queue")
    public void handleTicketAuditEvent(TicketCreatedEvent event) {
        log.info("Evento recibido en ticket-audit-queue para ticket: {}", event.id());
    }
}
