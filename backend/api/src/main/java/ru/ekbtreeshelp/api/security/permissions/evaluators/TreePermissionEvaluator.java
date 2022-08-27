package ru.ekbtreeshelp.api.security.permissions.evaluators;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ekbtreeshelp.api.security.permissions.constants.Roles;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.core.entity.Tree;
import ru.ekbtreeshelp.core.repository.TreeRepository;
import ru.ekbtreeshelp.api.security.permissions.constants.Domains;
import ru.ekbtreeshelp.api.security.permissions.constants.Permissions;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiFunction;

@Component(Domains.TREE + MainPermissionEvaluator.PERMISSION_EVALUATOR)
@RequiredArgsConstructor
public class TreePermissionEvaluator extends DomainPermissionEvaluatorBase {

    private final TreeRepository treeRepository;
    private final SecurityService securityService;

    private final Map<String, BiFunction<Authentication, Long, Boolean>> permissionCheckers = Map.of(
            Permissions.DELETE, this::canEditOrDelete,
            Permissions.EDIT, this::canEditOrDelete
    );

    @Override
    protected Long getAuthorId(Long entityId) {
        return treeRepository.findById(entityId).orElseThrow().getAuthor().getId();
    }

    @Override
    protected Map<String, BiFunction<Authentication, Long, Boolean>> getPermissionCheckers() {
        return permissionCheckers;
    }

    private boolean canEditOrDelete(Authentication authentication, Long treeId) {
        var treeOptional = treeRepository.findById(treeId);
        if (treeOptional.isEmpty()) {
            return false;
        }
        var tree = treeOptional.get();
        if (tree.isApprovedByModerator()) {
            return securityService.currentUserHasAnyRole(Roles.MODERATOR, Roles.SUPERUSER);
        }
        return userIsAuthor(authentication, treeId);
    }
}
