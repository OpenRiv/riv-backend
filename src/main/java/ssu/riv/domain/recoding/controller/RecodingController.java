package ssu.riv.domain.recoding.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{recodingId}")
    @Operation(summary = "요약본 텍스트 파일 조회 API", description = "특정 recodingId와 channelId로 레코딩 정보를 조회하는 API입니다.")
    public ResultResponse<RecodingResponse.GetRecodingInfo> getRecoding(@PathVariable Long recodingId) {

        // 레코딩 데이터 조회
        Recoding recoding = recodingService.getRecoding(recodingId);

        // 응답 데이터 변환
        return ResultResponse.of(RivResultCode.RECODING_INFO,
                recodingConverter.toRecodingInfo(recoding));
    }

    @PatchMapping("/{recodingId}")
    @Operation(summary = "요약본 텍스트 파일 수정 API", description = "recodingId를 기반으로 제목과 텍스트를 수정합니다.")
    public ResultResponse<RecodingResponse.UpdateRecodingInfo> updateRecoding(
            @PathVariable Long recodingId,
            @RequestBody RecodingRequest.UpdateRecodingRequest request) {

        Recoding updatedRecoding = recodingService.updateRecoding(recodingId, request);

        return ResultResponse.of(RivResultCode.UPDATE_RECODING,
                recodingConverter.toUpdateRecodingInfo(updatedRecoding));
    }

    @DeleteMapping("/{recodingId}")
    @Operation(summary = "요약본 텍스트 파일 삭제 API", description = "recodingId를 기반으로 레코딩을 삭제합니다.")
    public ResultResponse<RecodingResponse.DeleteRecodingInfo> deleteRecoding(@PathVariable Long recodingId) {
        Recoding deletedRecoding = recodingService.deleteRecoding(recodingId);

        return ResultResponse.of(RivResultCode.DELETE_RECODING,
                recodingConverter.toDeleteRecodingInfo(deletedRecoding));
    }

    @PostMapping("/{recodingId}/meeting-time")
    @Operation(summary = "레코딩 회의시간 저장 API", description = "특정 recodingId에 대한 회의 시작 시간과 종료 시간을 저장합니다.")
    public ResultResponse<RecodingResponse.SaveMeetingTimeInfo> saveMeetingTime(
            @PathVariable Long recodingId,
            @RequestBody RecodingRequest.SaveMeetingTimeRequest request) {

        Recoding updatedRecoding = recodingService.saveMeetingTime(recodingId, request.getStartTime(), request.getEndTime());

        return ResultResponse.of(RivResultCode.SAVE_MEETING_TIME,
                recodingConverter.toSaveMeetingTimeInfo(updatedRecoding));
    }
}
