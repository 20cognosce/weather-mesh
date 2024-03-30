SET TIMEZONE = 'Europe/Moscow';

CREATE TABLE IF NOT EXISTS system
(
    id                UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name              VARCHAR(255) NOT NULL UNIQUE,
    registration_time TIMESTAMP    NOT NULL,
    registration_type VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS status
(
    id    UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    value VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS permission
(
    id                     UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    request_from_system_id UUID NOT NULL,
    request_to_system_id   UUID NOT NULL,
    status_id              UUID NOT NULL,
    FOREIGN KEY (request_from_system_id) REFERENCES system (id),
    FOREIGN KEY (request_to_system_id) REFERENCES system (id),
    FOREIGN KEY (status_id) REFERENCES status (id)
);

create table IF NOT EXISTS request
(
    id                     UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    request_from_system_id UUID      NOT NULL,
    request_to_system_id   UUID      NOT NULL,
    status_id              UUID      NOT NULL,
    permission_id          UUID,
    timestamp              TIMESTAMP NOT NULL,
    FOREIGN KEY (request_from_system_id) REFERENCES system (id),
    FOREIGN KEY (request_to_system_id) REFERENCES system (id),
    FOREIGN KEY (status_id) REFERENCES status (id),
    FOREIGN KEY (permission_id) REFERENCES permission (id)
);

CREATE TABLE IF NOT EXISTS audit
(
    id            UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    old_status_id UUID         NOT NULL,
    new_status_id UUID         NOT NULL,
    permission_id UUID         NOT NULL,
    user_agent    VARCHAR(255) NOT NULL,
    timestamp     TIMESTAMP    NOT NULL,
    FOREIGN KEY (old_status_id) REFERENCES status (id),
    FOREIGN KEY (new_status_id) REFERENCES status (id),
    FOREIGN KEY (permission_id) REFERENCES permission (id)
);