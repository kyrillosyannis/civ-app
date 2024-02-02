DROP TABLE IF EXISTS PETITIONS;
CREATE TABLE petitions (
id serial  primary key,
title varchar(100) not null,
description varchar(1000) not null,
sector_id int,
user_id int,
number_of_signatures int
);

create table users (
id serial primary key,
username varchar(100) not null,
password varchar(100) not null
);

create index idx_users_username
on users(username);