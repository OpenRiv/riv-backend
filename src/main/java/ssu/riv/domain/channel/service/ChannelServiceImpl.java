package ssu.riv.domain.channel.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import ssu.riv.domain.channel.entity.Channel;
import ssu.riv.domain.channel.repository.ChannelRepository;
import ssu.riv.domain.recoding.repository.RecodingRepository;
import ssu.riv.domain.server.entity.Server;
import ssu.riv.domain.server.repository.ServerRepository;
import ssu.riv.global.error.BusinessException;
import ssu.riv.global.error.code.RivErrorCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final ServerRepository serverRepository;
    private final RecodingRepository recodingRepository;

    private final RestTemplate restTemplate;

    @Value("${discord.bot.token}")
    private String botToken;

    // 채널 추가
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

    // unique id로 특정 채널 조회
    @Override
    @Transactional(readOnly = true)
    public Channel getChannelId(String channelUnique) {
        return channelRepository.findByChannelUnique(channelUnique)
                .orElseThrow(() -> new BusinessException(RivErrorCode.CHANNEL_NOT_FOUND));
    }

    // 서버 ID로 모든 채널 ID를 조회
    @Override
    @Transactional(readOnly = true)
    public List<Long> getChannelList(Long serverId) {

        // 1. 서버가 존재하는지 확인
        Server server = serverRepository.findById(serverId)
                .orElseThrow(() -> new BusinessException(RivErrorCode.SERVER_NOT_FOUND));

        // 서버 ID로 채널 목록 조회
        return channelRepository.findByServerId(serverId)
                .stream()
                .map(Channel::getId) // DB에서 생성된 Long 타입 채널 ID 추출
                .collect(Collectors.toList());
    }

    // 디스코드로부터, 채널의 목록을 가져온다. (권한 우회)
    @Override
    public List<Long> getGuildChannel(Long guildId) {
        // 디스코드 API 엔드포인트 설정
        String url = "https://discord.com/api/guilds/" + guildId + "/channels";

        // 헤더에 봇 토큰 추가
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bot " + botToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 디스코드 API 호출
        ResponseEntity<Channel[]> response = restTemplate.exchange(url, HttpMethod.GET, entity, Channel[].class);

        // 채널 ID 목록 반환
        return Arrays.stream(Objects.requireNonNull(response.getBody()))
                .map(Channel::getId)
                .collect(Collectors.toList());
    }

    // 디스코드에서 어떻게 채널 데이터를 가져오는지에 대한 주석:
    // - 이 메서드는 디스코드 API의 `/guilds/{guildId}/channels` 엔드포인트를 호출하여 특정 서버에 속한 채널 목록을 가져옵니다.
    // - 호출 시 봇 토큰을 Authorization 헤더에 추가하여 인증합니다.
    // - API의 응답은 Channel 배열이며, 여기서 각 채널의 ID만 추출하여 반환합니다.

    @Override
    public List<String> getCategoriesByChannel(Long channelId) {
        // 1. 채널이 존재하는지 확인
        channelRepository.findById(channelId)
                .orElseThrow(() -> new BusinessException(RivErrorCode.CHANNEL_NOT_FOUND);

        // Repository를 통해 Distinct한 카테고리 목록 조회
        return recodingRepository.findDistinctCategoryByChannelId(channelId);
    }
}
