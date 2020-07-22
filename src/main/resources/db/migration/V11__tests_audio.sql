create table tests_audio (
    id int8 not null
  , guid varchar(36) not null
  , test_id int8 not null
  , audio text not null
  , primary key (id)
);

alter table if exists tests_audio
  add constraint tests_audio_test_fk foreign key (test_id) references tests
