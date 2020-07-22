create table tests_image (
    id int8 not null
  , guid varchar(36) not null
  , test_id int8 not null
  , image text not null
  , primary key (id)
);

alter table if exists tests_image
  add constraint tests_image_test_fk foreign key (test_id) references tests
