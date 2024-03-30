package ru.mirea.circuit.breaker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.circuit.breaker.entity.Audit;

import java.util.UUID;

@Repository
public interface AuditRepository extends JpaRepository<Audit, UUID> {

}
