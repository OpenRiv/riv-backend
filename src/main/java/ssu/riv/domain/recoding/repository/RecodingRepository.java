package ssu.riv.domain.recoding.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssu.riv.domain.recoding.entity.Recoding;

import java.util.List;

@Repository
public interface RecodingRepository extends JpaRepository<Recoding, Long> {
    Page<Recoding> findByChannelId(Long channelId, Pageable pageable);
    @Query("SELECT DISTINCT r.category FROM Recoding r WHERE r.channel.id = :channelId")
    List<String> findDistinctCategoryByChannelId(@Param("channelId") Long channelId);
    Page<Recoding> findByChannelIdAndCategory(Long channelId, String category, Pageable pageable);
    Page<Recoding> findByChannelIdAndTextContainingIgnoreCase(Long channelId, String text, Pageable pageable);
    Page<Recoding> findByChannelIdAndCategoryAndTextContainingIgnoreCase(Long channelId, String category, String text, Pageable pageable);
}
