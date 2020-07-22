create table statistics (
    id int8 not null
  , name varchar(255) not null
  , test_id int8 not null
  , user_id int8 not null
  , create_date date not null
  , result varchar(10000)
  , primary key (id)
);

alter table if exists statistics
  add constraint statistics_user_fk foreign key (user_id) references users,
  add constraint statistics_test_fk foreign key (test_id) references tests
