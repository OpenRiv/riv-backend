package ssu.riv.domain.channel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.riv.domain.channel.entity.Channel;
import ssu.riv.domain.channel.repository.ChannelRepository;
import ssu.riv.domain.server.entity.Server;
import ssu.riv.domain.server.repository.ServerRepository;
import ssu.riv.global.error.BusinessException;
import ssu.riv.global.error.code.RivErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;

    @Override
    public List<Long> addChannels(Long serverId, List<String> channelUniqueIds) {
        // 1. 서버가 존재하는지 확인
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new BusinessException(RivErrorCode.SERVER_NOT_FOUND));

        // 2. unique ID를 기반으로 채널 저장 및 DB 채널 ID 반환
        List<Long> savedChannelIds = new ArrayList<>();
        for (String channelUniqueId : channelUniqueIds) {
            Channel channel = Channel.builder()
                    .server(server)
                    .channelUnique(channelUniqueId)
                    .build();
            channelRepository.save(channel);
            savedChannelIds.add(channel.getId()); // DB에 생성된 Long ID를 저장
        }

        return savedChannelIds; // 방금 저장한 채널들의 ID 리스트 반환
    }

    // 서버 ID로 모든 채널 ID를 조회
    @Override
    @Transactional(readOnly = true)
    public List<Long> findChannelIds(Long serverId) {
        return channelRepository.findByServerId(serverId) // 서버 ID로 채널 조회
                .stream()
                .map(Channel::getId) // DB의 채널 ID만 추출
                .collect(Collectors.toList()); // 자바 8 버전
    }
}
