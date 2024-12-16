package ssu.riv.domain.recoding.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssu.riv.domain.recoding.dto.RecodingRequest;
import ssu.riv.domain.recoding.entity.Recoding;

import java.time.LocalDateTime;

public interface RecodingService {
    Recoding saveRecoding(RecodingRequest.SaveRecodingRequest request);
    Recoding getRecoding(Long recodingId);
    Recoding updateRecoding(Long recodingId, RecodingRequest.UpdateRecodingRequest request);
    Recoding deleteRecoding(Long recodingId);
    Page<Recoding> getRecodingList(Long channelId, Pageable pageable);
    Page<Recoding> getRecodingListByChannelAndCategory(Long channelId, String categoryName, Pageable pageable);
    Page<Recoding> getRecodingListByChannelAndSearch(Long channelId, String search, Pageable pageable);
    Page<Recoding> getRecodingListByChannelCategoryAndSearch(Long channelId, String categoryName, String search, Pageable pageable);
}
