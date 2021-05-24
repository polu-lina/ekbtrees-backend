package ru.naumen.ectmauth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.naumen.ectmauth.entity.Role;

import java.util.Collection;
import java.util.Set;

@Repository
@Transactional
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("from Role where name in (:names)")
    Set<Role> findAllByNames(Collection<String> names);

}
