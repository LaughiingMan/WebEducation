package com.diser.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(of = { "id" })
public class Statistics {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonView(Views.UserInfo.class)
    private Long id;

    @JsonView(Views.UserInfo.class)
    private String name;

    @JsonView(Views.UserInfo.class)
    @ManyToOne
    @JoinColumn(name="test_id")
    private Test test;

    @JsonView(Views.UserInfo.class)
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonView(Views.UserInfo.class)
    @Type(type="date")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Date createDate;

    @JsonView(Views.UserInfo.class)
    private String result;
}
