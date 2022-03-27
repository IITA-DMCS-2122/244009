#!/bin/sh

#psql -d todo_analytics -U postgres
psql --username postgres --command "CREATE DATABASE todo_analytics;"
psql --username postgres --command "GRANT ALL PRIVILEGES ON DATABASE todo_analytics TO postgres;"

psql --username postgres --dbname todo_analytics --command "CREATE USER analyst WITH PASSWORD 'analyst';"
psql --username postgres --dbname todo_analytics --command "GRANT CONNECT ON DATABASE todo_analytics TO analyst;"
psql --username postgres --dbname todo_analytics --command "GRANT USAGE ON SCHEMA public TO analyst;"
psql --username postgres --dbname todo_analytics --command "GRANT SELECT ON ALL TABLES IN SCHEMA public TO analyst;"
psql --username postgres --dbname todo_analytics --command "ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT ON TABLES TO analyst;"
