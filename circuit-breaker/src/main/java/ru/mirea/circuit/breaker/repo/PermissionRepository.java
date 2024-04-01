package ru.mirea.circuit.breaker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.mirea.circuit.breaker.entity.Permission;

import java.util.List;
import java.util.UUID;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, UUID> {

    @Query("SELECT p FROM Permission p " +
           "JOIN System s1 ON p.requestFromSystem.id = s1.id " +
           "JOIN System s2 ON p.requestToSystem.id = s2.id " +
           "WHERE s1.name = :requestFromSystemName AND s2.name = :requestToSystemName")
    List<Permission> findPermissionsBySystemNames(@Param("requestFromSystemName") String requestFromSystemName,
                                                  @Param("requestToSystemName") String requestToSystemName);
}
