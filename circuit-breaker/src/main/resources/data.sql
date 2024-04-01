CREATE OR REPLACE FUNCTION is_table_empty(table_name TEXT)
    RETURNS BOOLEAN AS
'
    DECLARE
        is_empty BOOLEAN;
    BEGIN
        EXECUTE format(''SELECT NOT EXISTS (SELECT * FROM %s LIMIT 1)'', table_name) INTO is_empty;
        RETURN is_empty;
    END;
' LANGUAGE plpgsql;

DO
'
DECLARE
    weather_id UUID;
    dictionary_id UUID;
    allowed_id UUID;
BEGIN
IF is_table_empty(''system'') THEN
    INSERT INTO system (name, registration_time, registration_type)
    VALUES
        (''weather'', localtimestamp(0), ''AUTOMATIC''),
        (''dictionary'', localtimestamp(0), ''AUTOMATIC''),
        (''circuit-breaker'', localtimestamp(0), ''AUTOMATIC'');
    END IF;

IF is_table_empty(''status'') THEN
    INSERT INTO status (value)
    VALUES
        (''ALLOWED''),
        (''FORBIDDEN'');
    END IF;

IF is_table_empty(''permission'') THEN
    SELECT id INTO weather_id FROM system WHERE name = ''weather'';
    SELECT id INTO dictionary_id FROM system WHERE name = ''dictionary'';
    SELECT id INTO allowed_id FROM status WHERE value = ''ALLOWED'';

    INSERT INTO permission (request_from_system_id, request_to_system_id, status_id)
    VALUES
        (weather_id, dictionary_id, allowed_id);
    END IF;
END
'