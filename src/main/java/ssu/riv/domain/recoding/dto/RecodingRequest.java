package ssu.riv.domain.recoding.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public abstract class RecodingRequest {

    // 녹음파일 저장 시
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveRecodingRequest {
        @NotNull(message = "채널 Id는 필수로 입력해야 합니다.")
        private Long channelId; // 채널 ID
        @NotNull(message = "제목은 필수로 입력해야 합니다.")
        private String title;   // 제목
        private String text;    // 텍스트
    }

    // 녹음파일 수정 시
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UpdateRecodingRequest {
        private String title; // 제목
        private String text;  // 텍스트
    }

    // 녹음시간 저장 시
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveMeetingTimeRequest {
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }
}
