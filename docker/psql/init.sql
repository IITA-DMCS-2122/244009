-- CREATE USER postgres WITH ENCRYPTED PASSWORD 'postgres';
CREATE USER analyst WITH ENCRYPTED PASSWORD 'analyst';
CREATE DATABASE todo_primary;
CREATE DATABASE todo_analytics;
GRANT ALL PRIVILEGES ON DATABASE todo_primary TO postgres;
GRANT ALL PRIVILEGES ON DATABASE todo_analytics TO postgres;

GRANT CONNECT ON DATABASE todo_analytics TO analyst;
GRANT USAGE ON SCHEMA public TO analyst;
GRANT SELECT ON ALL TABLES IN SCHEMA public TO analyst;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT SELECT ON TABLES TO analyst;