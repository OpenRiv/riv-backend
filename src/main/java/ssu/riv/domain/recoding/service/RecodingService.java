package ssu.riv.domain.recoding.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ssu.riv.domain.recoding.dto.RecodingRequest;
import ssu.riv.domain.recoding.entity.Recoding;

public interface RecodingService {
    Recoding saveRecoding(RecodingRequest.SaveRecodingRequest request);
    Recoding getRecoding(Long recodingId);
    Recoding updateRecoding(Long recodingId, RecodingRequest.UpdateRecodingRequest request);
    Recoding deleteRecoding(Long recodingId);
    Page<Recoding> getRecodingList(Long channelId, Pageable pageable);
}
