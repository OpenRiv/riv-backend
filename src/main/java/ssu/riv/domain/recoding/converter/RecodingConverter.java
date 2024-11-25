package ssu.riv.domain.recoding.converter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ssu.riv.domain.recoding.dto.RecodingResponse;
import ssu.riv.domain.recoding.entity.Recoding;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class RecodingConverter {

    public RecodingResponse.SaveRecodingInfo toSaveRecodingInfo(Recoding recoding) {
        return RecodingResponse.SaveRecodingInfo
                .builder()
                .recodingId(recoding.getId()) // Recoding ID
                .createdAt(recoding.getCreatedAt()) // 생성 시간
                .build();
    }

    public RecodingResponse.GetRecodingInfo toRecodingInfo(Recoding recoding) {
        return RecodingResponse.GetRecodingInfo
                .builder()
                .recodingId(recoding.getId())
                .title(recoding.getTitle())
                .text(recoding.getText())
                .createdAt(recoding.getCreatedAt())
                .build();
    }

    public RecodingResponse.UpdateRecodingInfo toUpdateRecodingInfo(Recoding recoding) {
        return RecodingResponse.UpdateRecodingInfo.builder()
                .recodingId(recoding.getId())
                .updatedAt(recoding.getUpdatedAt())
                .build();
    }

    public RecodingResponse.DeleteRecodingInfo toDeleteRecodingInfo(Recoding recoding) {
        return RecodingResponse.DeleteRecodingInfo.builder()
                .recodingId(recoding.getId())
                .deletedAt(LocalDateTime.now()) // 현재 시간을 삭제 시간으로 설정
                .build();
    }
}
