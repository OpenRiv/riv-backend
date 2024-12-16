package ssu.riv.domain.recoding.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .category(request.getCategoryName())
                .build();

        return recodingRepository.save(recoding);
    }

    @Override
    @Transactional(readOnly = true)
    public Recoding getRecoding(Long recodingId) {
        return findRecoding(recodingId);
    }

    @Override
    public Recoding updateRecoding(Long recodingId, RecodingRequest.UpdateRecodingRequest request) {
        // recodingId로 Recoding 조회
        Recoding recoding = findRecoding(recodingId);

        // 제목 수정
        if (request.getTitle() != null && !request.getTitle().isEmpty()) {
            recoding.setTitle(request.getTitle());
        }

        // 텍스트 수정
        if (request.getText() != null && !request.getText().isEmpty()) {
            recoding.setText(request.getText());
        }

        // 수정 시간 갱신
        recoding.setUpdatedAt(LocalDateTime.now());

        return recodingRepository.save(recoding); // 수정된 Recoding 저장 및 반환
    }

    @Override
    public Recoding deleteRecoding(Long recodingId) {
        // recodingId로 Recoding 조회
        Recoding recoding = findRecoding(recodingId);

        // 물리적 삭제 수행
        recodingRepository.delete(recoding);

        // 삭제 전의 Recoding 데이터 반환
        return recoding;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recoding> getRecodingList(Long channelId, Pageable pageable) {
        return recodingRepository.findByChannelId(channelId, pageable);
    }

    private Recoding findRecoding(Long recodingId) {
        return recodingRepository.findById(recodingId)
                .orElseThrow(() -> new BusinessException(RivErrorCode.RECODING_NOT_FOUND));
    }

}
