DROP TABLE IF EXISTS PETITIONS;
CREATE TABLE PETITIONS (
id INT AUTO_INCREMENT  PRIMARY KEY,
title VARCHAR(100) NOT NULL,
description VARCHAR(1000) NOT NULL,
sector_id INT,
user_id INT,
number_of_signatures INT
);

create table users (
id int auto_increment primary key,
username varchar(100) not null,
password varchar(100) not null,
);

CREATE INDEX idx_users_username
ON users(username);