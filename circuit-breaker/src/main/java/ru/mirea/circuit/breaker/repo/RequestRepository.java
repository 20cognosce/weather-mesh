package ru.mirea.circuit.breaker.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.circuit.breaker.entity.Request;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

}
