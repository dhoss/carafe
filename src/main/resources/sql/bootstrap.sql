CREATE DATABASE satchel;
-- change this, obviously.
CREATE USER satchel WITH ENCRYPTED PASSWORD 'satchel';
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON TABLES TO satchel;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT ALL ON SEQUENCES TO satchel;
GRANT ALL PRIVILEGES ON DATABASE satchel TO satchel;