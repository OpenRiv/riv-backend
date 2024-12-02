package ssu.riv.domain.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.riv.domain.server.entity.Server;

import java.util.Optional;

@Repository
public interface ServerRepository extends JpaRepository<Server, Long> {
    boolean existsByServerUnique(String serverUnique);
    Optional<Server> findByServerUnique(String uniqueId);
}
