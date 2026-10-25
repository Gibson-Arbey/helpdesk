# Helpdesk: ejecución y flujo

## Requisitos locales

- Java 21
- Docker Desktop con el motor Linux iniciado
- Gradle Wrapper incluido en el repositorio

Arranca PostgreSQL y RabbitMQ desde la raíz del proyecto:

```powershell
docker compose up -d ticket-db rabbitmq
```

Luego inicia la aplicación:

```powershell
.\gradlew.bat :app-service:bootRun
```

La API queda en `http://localhost:8080`. RabbitMQ Management queda en `http://localhost:15672` con `guest` / `guest`. Flyway aplica las migraciones al iniciar la aplicación.

Las propiedades del broker aceptan `RABBITMQ_HOST`, `RABBITMQ_PORT`, `RABBITMQ_USERNAME` y `RABBITMQ_PASSWORD`. Los valores por defecto apuntan al compose local.

## Crear y asignar un ticket

1. Registra un cliente con `POST /api/user/register-customer` y un agente con `POST /api/user/register-agent`. Las rutas de usuario usan la versión API `1` por defecto.
2. Inicia sesión con `POST /api/auth/login` y usa el token como `Authorization: Bearer <token>`.
3. El cliente crea un ticket con `POST /api/tickets` y un JSON como `{"title":"No puedo iniciar sesión","description":"La aplicación rechaza mis credenciales"}`. El cliente se toma del usuario autenticado.
4. Después de persistir el ticket, la aplicación publica `ticket.created` en el exchange topic durable `ticket-events`.
5. `ticket-assignment-queue` consume el evento y delega a `AssignTicketUseCase`. El adaptador JPA bloquea el ticket y el perfil seleccionado, confirma que sigan en los estados esperados y guarda el ticket asignado y el agente ocupado dentro de una transacción.
6. Al terminar la transacción, publica `ticket.assigned`. Las colas de notificación y auditoría reciben los eventos `ticket.#` y registran su routing key.

Los errores permanentes de negocio van a las colas DLQ. La falta temporal de capacidad usa tres intentos finitos antes de quedar en `ticket-assignment-dlq`. Revisa las colas `ticket-notification-dlq` y `ticket-audit-dlq` para errores de sus consumidores.

## API disponible

- `POST /api/tickets`, `GET /api/tickets` y `GET /api/tickets/{ticketId}`
- `GET /api/tickets/agent/{agentId}` y `GET /api/tickets/customer/{customerId}`
- `PATCH /api/tickets/{ticketId}/start`, `/resolve`, `/close` y `/cancel`
- `GET /api/agents/available` y `PATCH /api/agents/me/status`

El agente solo puede cambiar manualmente entre `AVAILABLE` y `OFFLINE`; `BUSY` se administra al asignar y completar tickets.

## Estados y fiabilidad

La migración de usuarios vigente usa UUID para clientes y agentes; los tickets conservan su ID numérico. El flujo mantiene la etapa `RESOLVED` ya existente: `IN_PROGRESS → RESOLVED → CLOSED`. Solo se cancelan tickets `CREATED` o `ASSIGNED`. Al cerrar o cancelar un ticket asignado, el perfil de agente vuelve a `AVAILABLE`.

La escritura en PostgreSQL y la publicación en RabbitMQ siguen siendo operaciones separadas. Los adaptadores confirman la transacción de base de datos antes de que el caso de uso publique el evento, así que un fallo del broker puede dejar el cambio persistido sin evento. El patrón Transactional Outbox es la mejora recomendada si se necesita entrega confiable y reintento coordinado.

Los límites de puntos y tickets de Bronce, Plata y Oro en `V3__create_user_and_leagues.sql` son valores iniciales de ejemplo para este proyecto educativo. Se conserva el nombre de columna existente `leag_maximunnumberofopentickets` para no cambiar el contrato de la migración.

## Temporadas y posiciones de agentes

El scheduler de temporadas se activa con `@EnableScheduling` y se ejecuta según `scheduler.tasks.league-season-expiration` (por defecto, el primer día de cada mes en `America/Bogota`). `LeagueSeasonExpirationUseCase` cierra las temporadas abiertas de ciclos anteriores estableciendo `lese_enddate` al último día del mes previo e inicia la temporada del mes actual para cada liga, con nombre `YYYY-MM` y fecha de inicio el primer día del mes. El ciclo no crea una temporada duplicada si ya existe la del mes o si hay otra temporada que debe permanecer activa. La persistencia del cierre y los inicios se realiza en una transacción del adaptador JPA; la inserción ignora conflictos con la clave única de liga y nombre, de modo que una ejecución repetida no duplique la temporada.

`leagueseasons` y `agentleaguestandings` mantienen sus relaciones con `leagues` y `agentprofiles`.

Una posición nueva inicia con cero puntos y sin puesto. El modelo valida incrementos de puntos y puestos positivos, pero el repositorio actual no define una política para otorgar puntos por tickets ni un cálculo automático de ranking. Tampoco hay todavía una ruta REST para crear temporadas o administrar posiciones.
