package ssu.riv.domain.recoding.converter;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ssu.riv.domain.recoding.dto.RecodingResponse;
import ssu.riv.domain.recoding.entity.Recoding;

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
}
