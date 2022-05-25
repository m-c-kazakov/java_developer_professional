-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)
/*
create table client
(
    id   bigserial not null primary key,
    name varchar(50)
);

 */

-- Для @GeneratedValue(strategy = GenerationType.SEQUENCE)

drop table if exists client;
drop table if exists addresses;
drop table if exists phones;
drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id bigint
);

create table addresses
(
    id   bigint not null primary key,
    street varchar(50)
);

create table phones
(
    id   bigint not null primary key,
    number varchar(50),
    client_id bigint
);
