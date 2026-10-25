package co.rabbitmq.listener;

import co.model.ticketevent.TicketCreatedEvent;
import co.model.agentprofile.exception.NoAvailableAgentException;
import co.model.agentprofile.exception.AgentUnavailableException;
import co.model.exception.DomainException;
import co.usecase.ticket.AssignTicketUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TicketCreatedEventListener {

    private final AssignTicketUseCase assignTicketUseCase;

    @RabbitListener(queues = "ticket-assignment-queue")
    public void handleTicketCreatedEvent(TicketCreatedEvent event) {
        if (event == null || event.id() == null) {
            throw new AmqpRejectAndDontRequeueException("Ticket-created event must include a ticket id");
        }
        log.info("Evento recibido en ticket-assignment-queue para ticket: {}", event.id());
        try {
            assignTicketUseCase.execute(event.id());
        } catch (NoAvailableAgentException | AgentUnavailableException transientCapacityError) {
            throw transientCapacityError;
        } catch (DomainException permanentBusinessError) {
            throw new AmqpRejectAndDontRequeueException(
                    "Ticket-created event cannot be processed: " + permanentBusinessError.getCode(),
                    permanentBusinessError
            );
        }
    }

    @RabbitListener(queues = "ticket-notification-queue")
    public void handleTicketNotificationEvent(Message message) {
        log.info("Evento recibido en ticket-notification-queue con routing key: {}",
                message.getMessageProperties().getReceivedRoutingKey());
    }

    @RabbitListener(queues = "ticket-audit-queue")
    public void handleTicketAuditEvent(Message message) {
        log.info("Evento recibido en ticket-audit-queue con routing key: {}",
                message.getMessageProperties().getReceivedRoutingKey());
    }
}
