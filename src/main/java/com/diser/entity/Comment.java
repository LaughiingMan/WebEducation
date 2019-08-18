package com.diser.entity;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Table
@Data
@EqualsAndHashCode(of = {"id"})
public class Comment {

    @Id
    @GeneratedValue
    @JsonView(Views.IdName.class)
    private Long id;
    @JsonView(Views.IdName.class)
    private String text;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private Message message;
}
