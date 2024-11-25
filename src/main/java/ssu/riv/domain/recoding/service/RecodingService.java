package ssu.riv.domain.recoding.service;

import ssu.riv.domain.recoding.dto.RecodingRequest;
import ssu.riv.domain.recoding.entity.Recoding;

public interface RecodingService {
    Recoding saveRecoding(RecodingRequest.SaveRecodingRequest request);
    Recoding getRecoding(Long recodingId);
    Recoding updateRecoding(Long recodingId, RecodingRequest.UpdateRecodingRequest request);
}
