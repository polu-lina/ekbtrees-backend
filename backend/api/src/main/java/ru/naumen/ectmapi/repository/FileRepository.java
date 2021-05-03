package ru.naumen.ectmapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.naumen.ectmapi.entity.FileDescription;

@Repository
public interface FileRepository extends JpaRepository<FileDescription, Long> {
    long countByHash(String hash);
    FileDescription getFirstByHash(String hash);
}
