package com.diser.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table(name = "tests_audio")
@Data
@EqualsAndHashCode(of = { "guid" })
public class TestAudio {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.UserInfo.class)
    private Long id;

    @JsonView(Views.UserInfo.class)
    private String guid;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonView(Views.UserInfo.class)
    private Test test;

    @Lob
    @JsonView(Views.UserInfo.class)
    private String audio;
}
