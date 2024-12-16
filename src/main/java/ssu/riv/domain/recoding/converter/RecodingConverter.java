package ssu.riv.domain.recoding.converter;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import ssu.riv.domain.recoding.dto.RecodingResponse;
import ssu.riv.domain.recoding.entity.Recoding;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecodingConverter {

    public RecodingResponse.SaveRecodingInfo toSaveRecodingInfo(Recoding recoding) {
        return RecodingResponse.SaveRecodingInfo
                .builder()
                .recodingId(recoding.getId()) // Recoding ID
                .createdAt(recoding.getCreatedAt()) // 생성 시간
                .startTime(recoding.getStartTime())
                .endTime(recoding.getEndTime())
                .category(recoding.getCategory())
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

    //페이징 처리해서 요약본 목록 반환
    public RecodingResponse.PagedRecodingInfo toPagedRecodingInfo(Page<Recoding> recodingPage) {

        List<RecodingResponse.TextInfo> textInfoList = recodingPage
                .getContent()
                .stream()
                .map(this::toFiftyTextInfo) //각 레코딩을 컨버터 사용해 변환
                .collect(Collectors.toList());

        return RecodingResponse.PagedRecodingInfo
                .builder()
                .textInfoList(textInfoList)
                .pages(recodingPage.getTotalPages())
                .totalElements(recodingPage.getTotalElements())
                .isFirst(recodingPage.isFirst())
                .isLast(recodingPage.isLast())
                .build();
    }

    private RecodingResponse.TextInfo toFiftyTextInfo(Recoding recoding) {
        return RecodingResponse.TextInfo
                .builder()
                .recodingId(recoding.getId())
                .title(recoding.getTitle())
                .subtext(generateSubtext(recoding.getText())) //각 레코딩을 "50자 + ... "만 보이게 변환하기
                .createdAt(recoding.getCreatedAt())
                .build();
    }

    // 텍스트를 50자까지만 보여주고 "..." 추가
    private String generateSubtext(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }
        //텍스트가 50자 이상이면 ... 처리, 아니면 그냥 보여줌
        return text.length() > 50 ? text.substring(0, 50) + "..." : text;
    }


}
