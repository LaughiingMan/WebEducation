create table user_roles (
    user_id int8 not null
  , roles varchar(255)
);

alter table if exists user_roles
  add constraint user_roles_user_fk foreign key (user_id) references users