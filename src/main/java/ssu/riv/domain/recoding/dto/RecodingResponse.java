package ssu.riv.domain.recoding.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public abstract class RecodingResponse {

    //파일 저장 시
    @Getter
    @Builder
    public static class SaveRecodingInfo {
        private Long recodingId;     // 생성된 Recoding ID
        private LocalDateTime createdAt; // 생성된 시간
    }

    //파일 조회 시
    @Getter
    @Builder
    public static class GetRecodingInfo {
        private Long recodingId;
        private String title;
        private String text;
        private LocalDateTime createdAt;
    }
}
