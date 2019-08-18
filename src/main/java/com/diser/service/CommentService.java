package com.diser.service;

import com.diser.entity.Comment;
import com.diser.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment create(Comment comment) {
        commentRepository.save(comment);
        return comment;
    }
}
