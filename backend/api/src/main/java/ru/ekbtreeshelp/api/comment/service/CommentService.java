package ru.ekbtreeshelp.api.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ekbtreeshelp.api.comment.mapper.CommentMapper;
import ru.ekbtreeshelp.api.comment.dto.CreateCommentDto;
import ru.ekbtreeshelp.api.comment.dto.UpdateCommentDto;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.core.entity.Comment;
import ru.ekbtreeshelp.core.repository.CommentRepository;
import ru.ekbtreeshelp.core.repository.TreeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final TreeRepository treeRepository;
    private final SecurityService securityService;

    @Transactional
    public Long create(CreateCommentDto createCommentDto) {
        Comment comment = commentMapper.fromDto(createCommentDto);
        comment.setAuthor(securityService.getCurrentUser());
        return commentRepository.save(comment).getId();
    }

    public List<Comment> getAllByTreeId(Long treeId) {
        return commentRepository.findAllByTreeId(treeId);
    }

    @Transactional
    public void update(Long id, UpdateCommentDto updateCommentDto) {
        commentMapper.updateFromDto(updateCommentDto, commentRepository.getOne(id));
    }

    @Transactional
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }
}
