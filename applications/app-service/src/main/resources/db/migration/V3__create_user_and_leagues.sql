-- V1 creates tickets. This guard also restores the table if it was removed
-- manually after V1/V2 had already been recorded by Flyway.
CREATE TABLE IF NOT EXISTS tickets (
    tick_id BIGSERIAL PRIMARY KEY,
    tick_title VARCHAR(255) NOT NULL,
    tick_description TEXT NOT NULL,
    cust_id BIGINT NOT NULL,
    agen_id BIGINT,
    tick_status VARCHAR(50) NOT NULL,
    tick_assignedon TIMESTAMP,
    tick_createdon TIMESTAMP NOT NULL,
    CONSTRAINT fk_ticket_customer FOREIGN KEY (cust_id) REFERENCES customers(cust_id),
    CONSTRAINT fk_ticket_agent FOREIGN KEY (agen_id) REFERENCES agents(agen_id)
);

CREATE INDEX IF NOT EXISTS idx_ticket_customer ON tickets(cust_id);
CREATE INDEX IF NOT EXISTS idx_ticket_agent ON tickets(agen_id);
CREATE INDEX IF NOT EXISTS idx_ticket_status ON tickets(tick_status);

CREATE TABLE users (
    user_id UUID PRIMARY KEY,
    user_name VARCHAR(100) NOT NULL,
    user_email VARCHAR(255) NOT NULL UNIQUE,
    user_password VARCHAR(255) NOT NULL,
    user_role VARCHAR(20) NOT NULL,
    CONSTRAINT chk_users_role
        CHECK (user_role IN ('CUSTOMER', 'AGENT', 'ADMIN'))
);

CREATE TABLE agentprofiles (
    agpr_id UUID PRIMARY KEY,
    user_id UUID NOT NULL UNIQUE,
    agpr_status VARCHAR(50) NOT NULL,
    agpr_lastticketclosedat TIMESTAMP,
    CONSTRAINT fk_agentprofiles_user
        FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT chk_agentprofiles_status
        CHECK (agpr_status IN ('AVAILABLE', 'BUSY', 'OFFLINE'))
);

CREATE INDEX idx_agentprofiles_status ON agentprofiles(agpr_status);

CREATE TABLE leagues (
    leag_id UUID PRIMARY KEY,
    leag_name VARCHAR(100) NOT NULL UNIQUE,
    leag_description TEXT,
    leag_maximunnumberofopentickets INTEGER NOT NULL,
    leag_maximumnumberofpoints INTEGER NOT NULL,
    leag_minimumnumberofpoints INTEGER NOT NULL
);

-- Seed values define simple starter tiers for the teaching project.
INSERT INTO leagues (
    leag_id,
    leag_name,
    leag_description,
    leag_maximunnumberofopentickets,
    leag_maximumnumberofpoints,
    leag_minimumnumberofpoints
)
VALUES
    (md5('league:bronze')::uuid, 'Bronce', 'Nivel inicial de la liga.', 5, 100, 0),
    (md5('league:silver')::uuid, 'Plata', 'Nivel intermedio de la liga.', 10, 250, 100),
    (md5('league:gold')::uuid, 'Oro', 'Nivel avanzado de la liga.', 20, 500, 250);

CREATE TABLE leagueseasons (
    lese_id UUID PRIMARY KEY,
    leag_id UUID NOT NULL,
    lese_name VARCHAR(100) NOT NULL,
    lese_startdate DATE NOT NULL,
    lese_enddate DATE,
    CONSTRAINT fk_leagueseasons_league
        FOREIGN KEY (leag_id) REFERENCES leagues(leag_id),
    CONSTRAINT uq_leagueseasons_league_name
        UNIQUE (leag_id, lese_name),
    CONSTRAINT chk_leagueseasons_dates
        CHECK (lese_enddate IS NULL OR lese_enddate >= lese_startdate)
);

CREATE TABLE agentleaguestandings (
    agls_id UUID PRIMARY KEY,
    agpr_id UUID NOT NULL,
    lese_id UUID NOT NULL,
    agls_points INTEGER NOT NULL DEFAULT 0,
    agls_position INTEGER,
    CONSTRAINT fk_agentleaguestandings_agentprofile
        FOREIGN KEY (agpr_id) REFERENCES agentprofiles(agpr_id),
    CONSTRAINT fk_agentleaguestandings_season
        FOREIGN KEY (lese_id) REFERENCES leagueseasons(lese_id),
    CONSTRAINT uq_agentleaguestandings_agent_season
        UNIQUE (agpr_id, lese_id),
    CONSTRAINT chk_agentleaguestandings_points
        CHECK (agls_points >= 0),
    CONSTRAINT chk_agentleaguestandings_position
        CHECK (agls_position IS NULL OR agls_position > 0)
);

CREATE INDEX idx_agentleaguestandings_season ON agentleaguestandings(lese_id);

-- The old seed has no passwords. The BCrypt value is generated from a random
-- secret which is not stored here; legacy accounts need password reset to log in.
INSERT INTO users (user_id, user_name, user_email, user_password, user_role)
SELECT md5('legacy-customer:' || c.cust_id::text)::uuid,
       c.cust_name,
       c.cust_email,
       '$2a$10$vQ.vCDyKfTffzATJkM75Keg2j63zLgPX9rOFg07D07lzptf0O31RG',
       'CUSTOMER'
FROM customers c
UNION ALL
SELECT md5('legacy-agent:' || a.agen_id::text)::uuid,
       a.agen_name,
       a.agen_email,
       '$2a$10$vQ.vCDyKfTffzATJkM75Keg2j63zLgPX9rOFg07D07lzptf0O31RG',
       'AGENT'
FROM agents a;

INSERT INTO agentprofiles (agpr_id, user_id, agpr_status, agpr_lastticketclosedat)
SELECT md5('legacy-agent-profile:' || a.agen_id::text)::uuid,
       md5('legacy-agent:' || a.agen_id::text)::uuid,
       a.agen_status,
       NULL
FROM agents a;

ALTER TABLE tickets DROP CONSTRAINT IF EXISTS fk_ticket_customer;
ALTER TABLE tickets DROP CONSTRAINT IF EXISTS fk_ticket_agent;

ALTER TABLE tickets
    ALTER COLUMN cust_id TYPE UUID
    USING md5('legacy-customer:' || cust_id::text)::uuid;

ALTER TABLE tickets
    ALTER COLUMN agen_id TYPE UUID
    USING CASE
        WHEN agen_id IS NULL THEN NULL::uuid
        ELSE md5('legacy-agent:' || agen_id::text)::uuid
    END;

ALTER TABLE tickets
    ADD CONSTRAINT fk_ticket_customer_user
        FOREIGN KEY (cust_id) REFERENCES users(user_id),
    ADD CONSTRAINT fk_ticket_agent_user
        FOREIGN KEY (agen_id) REFERENCES users(user_id);

DROP TABLE customers;
DROP TABLE agents;
