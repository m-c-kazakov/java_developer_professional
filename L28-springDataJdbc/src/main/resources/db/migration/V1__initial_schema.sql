create table "user"
(
    id bigserial not null primary key,
    name varchar(50) not null,
    login varchar(50) not null,
    password varchar(50) not null
);