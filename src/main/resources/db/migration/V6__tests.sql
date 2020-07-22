create table tests (
    id int8 not null
  , name varchar(255) not null
  , author_id int8 not null
  , create_date date not null
  , result varchar(10000)
  , primary key (id)
);

alter table if exists tests
  add constraint tests_author_fk foreign key (author_id) references users
