package ru.mirea.circuit.breaker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.circuit.breaker.entity.Status;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {
    Optional<Status> findByValue(String value);
}
