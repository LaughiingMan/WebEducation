create sequence hibernate_sequence start 1 increment 1;

create table users (
    id int8 not null
  , active boolean not null
  , email varchar(255) not null
  , password varchar(255) not null
  , userName varchar(255) not null
  , firstName varchar(255) not null
  , lastName varchar(255) not null
  , primary key (id)
);
