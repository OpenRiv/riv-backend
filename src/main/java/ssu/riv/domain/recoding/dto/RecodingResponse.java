package ssu.riv.domain.recoding.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public abstract class RecodingResponse {

    //파일 저장 시
    @Getter
    @Builder
    public static class SaveRecodingInfo {
        private Long recodingId;     // 생성된 Recoding ID
        private LocalDateTime createdAt; // 생성된 시간
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String category;
    }

    //유니크 id로 파일 저장 시
    @Getter
    @Builder
    public static class SaveRecodingInfoByUnique {
        private Long recodingId;     // 생성된 Recoding ID
        private LocalDateTime createdAt; // 생성된 시간
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private String category;
    }

    //파일 조회 시
    @Getter
    @Builder
    public static class GetRecodingInfo {
        private Long recodingId;
        private String title;
        private String text;
        private LocalDateTime createdAt;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
    }

    //파일 수정 시
    @Getter
    @Builder
    public static class UpdateRecodingInfo {
        private Long recodingId;     // 레코딩 ID
        private LocalDateTime updatedAt; // 수정된 시간
    }

    //파일 삭제 시
    @Getter
    @Builder
    public static class DeleteRecodingInfo {
        private Long recodingId;     // 삭제된 레코딩 ID
        private LocalDateTime deletedAt; // 삭제 시간
    }

    // 페이징 처리된 요약본 목록
    @Getter
    @Builder
    public static class PagedRecodingInfo {
        private List<TextInfo> textInfoList; // 요약본 정보 리스트
        private int pages;                  // 총 페이지 수
        private long totalElements;         // 총 요소 수
        private boolean isFirst;            // 첫 번째 페이지 여부
        private boolean isLast;             // 마지막 페이지 여부
    }

    // 요약본 1개 반환
    @Getter
    @Builder
    public static class TextInfo {
        private Long recodingId;       // 요약본 ID
        private String title;          // 제목
        private String subtext;        // 텍스트 요약 (50자 + ...) 처리
        private LocalDateTime createdAt; // 생성 시간
    }

}
