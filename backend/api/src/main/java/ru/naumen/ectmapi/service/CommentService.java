package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.naumen.ectmapi.entity.Comment;
import ru.naumen.ectmapi.repository.CommentRepository;
import ru.naumen.ectmapi.repository.TreeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class CommentService {

    private final CommentRepository commentRepository;
    private final TreeRepository treeRepository;

    public void save(Comment comment) {
        if(!treeRepository.isExists(comment.getTreeId())) {
            throw new IllegalStateException("Tree does not exist");
        }

        commentRepository.create(comment);
    }

    public Comment get(Long id) {
        return commentRepository.find(id);
    }

    public List<Comment> getAllByTreeId(Long treeId) {
        return commentRepository.findAllByTreeId(treeId);
    }

    public void delete(Long id) {
        commentRepository.delete(id);
    }
}
