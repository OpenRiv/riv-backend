package ssu.riv.domain.recoding.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.riv.domain.recoding.entity.Recoding;

@Repository
public interface RecodingRepository extends JpaRepository<Recoding, Long> {
    Page<Recoding> findByChannelId(Long channelId, Pageable pageable);
}
