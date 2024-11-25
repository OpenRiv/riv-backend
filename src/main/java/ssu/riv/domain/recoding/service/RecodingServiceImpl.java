package ssu.riv.domain.recoding.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssu.riv.domain.channel.entity.Channel;
import ssu.riv.domain.channel.repository.ChannelRepository;
import ssu.riv.domain.recoding.dto.RecodingRequest;
import ssu.riv.domain.recoding.entity.Recoding;
import ssu.riv.domain.recoding.repository.RecodingRepository;
import ssu.riv.global.error.BusinessException;
import ssu.riv.global.error.code.RivErrorCode;

import java.time.LocalDateTime;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class RecodingServiceImpl implements RecodingService {
    private final RecodingRepository recodingRepository;
    private final ChannelRepository channelRepository;

    @Override
    public Recoding saveRecoding(RecodingRequest.SaveRecodingRequest request) {

        // 채널이 존재하는지 확인
        Channel channel = channelRepository.findById(request.getChannelId())
                .orElseThrow(() -> new BusinessException(RivErrorCode.CHANNEL_NOT_FOUND));

        // Recoding 생성
        Recoding recoding = Recoding
                .builder()
                .channel(channel)
                .title(request.getTitle())
                .text(request.getText())
                .createdAt(LocalDateTime.now()) // 생성 시간 저장
                .build();

        return recodingRepository.save(recoding);
    }
}
