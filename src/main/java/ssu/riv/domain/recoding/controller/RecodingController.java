package ssu.riv.domain.recoding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssu.riv.domain.recoding.converter.RecodingConverter;
import ssu.riv.domain.recoding.dto.RecodingRequest;
import ssu.riv.domain.recoding.dto.RecodingResponse;
import ssu.riv.domain.recoding.entity.Recoding;
import ssu.riv.domain.recoding.service.RecodingService;
import ssu.riv.global.result.ResultResponse;
import ssu.riv.global.result.code.RivResultCode;

@RestController
@RequestMapping("/recoding")
@Tag(name = "03. Recoding API", description = "요약본 텍스트 파일을 CRUD하는 API입니다.")
@RequiredArgsConstructor
public class RecodingController {

    private final RecodingService recodingService;
    private final RecodingConverter recodingConverter;

    @PostMapping("")
    @Operation(summary = "요약본 텍스트 파일 저장 API", description = "요약본 텍스트 파일을 저장하는 API입니다.")
    public ResultResponse<RecodingResponse.SaveRecodingInfo> saveRecoding(@RequestBody RecodingRequest.SaveRecodingRequest request) {

        Recoding recoding = recodingService.saveRecoding(request);

        return ResultResponse.of(RivResultCode.SAVE_RECODING,
                recodingConverter.toSaveRecodingInfo(recoding));
    }
}
