-- =========================
-- CUSTOMERS
-- =========================

INSERT INTO customers (cust_name, cust_email)
VALUES
    ('Juan Pérez', 'juan.perez@email.com'),
    ('María Gómez', 'maria.gomez@email.com'),
    ('Carlos Rodríguez', 'carlos.rodriguez@email.com'),
    ('Ana Martínez', 'ana.martinez@email.com'),
    ('Luis Herrera', 'luis.herrera@email.com');


-- =========================
-- AGENTS
-- =========================

INSERT INTO agents (agen_id, agen_name, agen_email, agen_status)
VALUES
    (1, 'Pedro Sánchez', 'pedro.sanchez@support.com', 'AVAILABLE'),
    (2, 'Laura Torres', 'laura.torres@support.com', 'BUSY'),
    (3, 'Andrés Moreno', 'andres.moreno@support.com', 'AVAILABLE'),
    (4, 'Sofía Castro', 'sofia.castro@support.com', 'OFFLINE');


-- =========================
-- TICKETS
-- =========================

INSERT INTO tickets (
    tick_title,
    tick_description,
    cust_id,
    agen_id,
    tick_status,
    tick_assignedon,
    tick_createdon
)
VALUES
    (
        'No puedo iniciar sesión',
        'La aplicación indica credenciales inválidas.',
        1,
        NULL,
        'CREATED',
        NULL,
        NOW() - INTERVAL '2 day'
    ),
    (
        'Error al realizar pago',
        'El pago fue rechazado varias veces.',
        2,
        1,
        'ASSIGNED',
        NOW() - INTERVAL '1 day',
        NOW() - INTERVAL '2 day'
    ),
    (
        'No recibo correos',
        'Las notificaciones no llegan al correo registrado.',
        3,
        2,
        'IN_PROGRESS',
        NOW() - INTERVAL '3 day',
        NOW() - INTERVAL '4 day'
    ),
    (
        'Solicitud de reembolso',
        'Deseo solicitar devolución del dinero.',
        4,
        2,
        'CLOSED',
        NOW() - INTERVAL '5 day',
        NOW() - INTERVAL '6 day'
    ),
    (
        'Problema con actualización',
        'La aplicación falla después de actualizar.',
        5,
        NULL,
        'CREATED',
        NULL,
        NOW() - INTERVAL '6 hour'
    ),
    (
        'Cuenta bloqueada',
        'Mi cuenta fue bloqueada sin motivo.',
        1,
        3,
        'ASSIGNED',
        NOW() - INTERVAL '4 hour',
        NOW() - INTERVAL '1 day'
    ),
    (
        'Consulta de facturación',
        'No entiendo un cobro realizado.',
        2,
        1,
        'IN_PROGRESS',
        NOW() - INTERVAL '12 hour',
        NOW() - INTERVAL '2 day'
    ),
    (
        'Cancelar suscripción',
        'Deseo cancelar mi plan actual.',
        3,
        3,
        'CANCELLED',
        NOW() - INTERVAL '7 day',
        NOW() - INTERVAL '8 day'
    );