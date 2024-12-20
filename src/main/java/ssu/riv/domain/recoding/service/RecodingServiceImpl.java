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
import ssu.riv.domain.server.entity.Server;
import ssu.riv.domain.server.repository.ServerRepository;
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
    private final ServerRepository serverRepository;

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
    public Recoding saveRecodingByUnique(RecodingRequest.SaveRecodingRequestByUnique request) {

        // 1. 서버가 존재하는지 확인
        Server server = serverRepository.findByServerUnique(request.getServerUniqueId())
                .orElseThrow(() -> new BusinessException(RivErrorCode.SERVER_NOT_FOUND));

        // request.getUniqueId()를 이용해 채널 조회
        Channel channel = channelRepository.findByChannelUnique(request.getChannelUniqueId())
                .orElseGet(() -> {
                    // 채널이 없으면 새로 생성
                    Channel newChannel = Channel.builder()
                            .channelUnique(request.getChannelUniqueId())
                            .server(server)
                            .build();
                    return channelRepository.save(newChannel);
                });

        // Recoding 엔티티 생성
        Recoding recoding = Recoding.builder()
                .channel(channel)
                .title(request.getTitle())
                .text(request.getText())
                .createdAt(LocalDateTime.now()) // 생성 시간 저장
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .category(request.getCategoryName())
                .build();

        // 저장
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
        // 채널이 존재하는지 확인
        findChannel(channelId);
        return recodingRepository.findByChannelId(channelId, pageable);
    }

    //특정 채널과 카테고리에 해당하는 레코딩 목록 조회 (페이징)
    @Override
    @Transactional(readOnly = true)
    public Page<Recoding> getRecodingListByChannelAndCategory(Long channelId, String categoryName, Pageable pageable) {
        findChannel(channelId);
        return recodingRepository.findByChannelIdAndCategory(channelId, categoryName, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recoding> getRecodingListByChannelAndSearch(Long channelId, String search, Pageable pageable) {
        findChannel(channelId);
        // text 필드에 search 포함하는 조건 추가
        return recodingRepository.findByChannelIdAndTextContainingIgnoreCase(channelId, search, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Recoding> getRecodingListByChannelCategoryAndSearch(Long channelId, String categoryName, String search, Pageable pageable) {
        findChannel(channelId);
        // 특정 채널, 특정 카테고리, 그리고 text에 search가 포함되는 레코딩 조회
        return recodingRepository.findByChannelIdAndCategoryAndTextContainingIgnoreCase(channelId, categoryName, search, pageable);
    }

    private Recoding findRecoding(Long recodingId) {
        return recodingRepository.findById(recodingId)
                .orElseThrow(() -> new BusinessException(RivErrorCode.RECODING_NOT_FOUND));
    }

    private void findChannel(Long channelId) {
        // 채널이 존재하는지 확인
        channelRepository.findById(channelId)
                .orElseThrow(() -> new BusinessException(RivErrorCode.CHANNEL_NOT_FOUND));

    }
}
