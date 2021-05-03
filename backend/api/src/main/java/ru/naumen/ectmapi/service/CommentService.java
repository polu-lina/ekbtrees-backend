package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmapi.entity.Comment;
import ru.naumen.ectmapi.repository.CommentRepository;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CommentService {

    private final CommentRepository commentRepository;

    public void save(Comment comment){
        commentRepository.save(comment);
    }

    public Comment get(Long id){
        return commentRepository.findById(id).orElseThrow();
    }

    public void delete(Long id) {commentRepository.deleteById(id);}

}
