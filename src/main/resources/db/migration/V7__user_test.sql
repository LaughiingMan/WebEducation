create table user_test (
    user_id int8 not null
  , test_id int8 not null
  , result varchar(10000)
);

alter table if exists user_test
  add constraint user_test_user_fk foreign key (user_id) references users,
  add constraint user_test_test_fk foreign key (test_id) references tests