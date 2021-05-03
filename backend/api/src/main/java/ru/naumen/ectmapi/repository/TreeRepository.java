package ru.naumen.ectmapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naumen.ectmapi.entity.Tree;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
}
