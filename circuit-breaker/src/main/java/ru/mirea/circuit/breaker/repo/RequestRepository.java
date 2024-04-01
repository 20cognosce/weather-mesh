package ru.mirea.circuit.breaker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.circuit.breaker.entity.CircuitBreakerRequest;

@Repository
public interface RequestRepository extends JpaRepository<CircuitBreakerRequest, Long> {

}
