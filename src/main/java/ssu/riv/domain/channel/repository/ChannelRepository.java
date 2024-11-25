package ssu.riv.domain.channel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssu.riv.domain.channel.entity.Channel;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {
    // 특정 서버에 속한 모든 채널 조회
    List<Channel> findByServerId(Long serverId);
    Optional<Channel> findByChannelUnique(String uniqueId);
}
