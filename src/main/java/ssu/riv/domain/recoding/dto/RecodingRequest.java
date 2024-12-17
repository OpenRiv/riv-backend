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
        private String categoryName; // 카테고리명 문자열 필드
        private LocalDateTime startTime; // 회의 시작 시간
        private LocalDateTime endTime; // 회의 종료 시간
    }

    // UniqueId로 녹음파일 저장 시
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SaveRecodingRequestByUnique {
        @NotNull(message = "서버 Id는 필수로 입력해야 합니다.")
        private String serverUniqueId; // 서버 Unique ID
        @NotNull(message = "채널 Id는 필수로 입력해야 합니다.")
        private String channelUniqueId; // 채널 Unique ID
        @NotNull(message = "제목은 필수로 입력해야 합니다.")
        private String title;   // 제목
        private String text;    // 텍스트
        private String categoryName; // 카테고리명 문자열 필드
        private LocalDateTime startTime; // 회의 시작 시간
        private LocalDateTime endTime; // 회의 종료 시간
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
}
