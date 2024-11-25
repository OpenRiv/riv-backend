package ssu.riv.domain.recoding.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.riv.domain.recoding.entity.Recoding;

@Repository
public interface RecodingRepository extends JpaRepository<Recoding, Long> {
}
