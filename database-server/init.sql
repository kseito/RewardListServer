-- create database
create role test_user login password 'test_password';
create database testdb;
grant all privileges on database testdb to test_user;
\c testdb

-- create schema
create schema test_schema;
alter schema test_schema owner to test_user;

-- create table
CREATE TABLE IF NOT EXISTS test_schema.user (
    id          SERIAL PRIMARY KEY,
    todoist_id  BIGINT          NOT NULL,
    point       INTEGER         DEFAULT 0,
    UNIQUE(todoist_id)
);
alter table test_schema.user owner to test_user;