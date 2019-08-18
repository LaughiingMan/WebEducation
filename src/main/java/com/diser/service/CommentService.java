package com.diser.service;

import com.diser.dto.EventType;
import com.diser.dto.ObjectType;
import com.diser.entity.Comment;
import com.diser.entity.Views;
import com.diser.repository.CommentRepository;
import com.diser.util.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.BiConsumer;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepository commentRepository, WsSender wsSender) {
        this.commentRepository = commentRepository;
        this.wsSender = wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment) {
        Comment createComment = commentRepository.save(comment);
        wsSender.accept(EventType.CREATE, createComment);
        return createComment;
    }
}
