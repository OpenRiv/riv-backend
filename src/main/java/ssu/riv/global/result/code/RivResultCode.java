package ssu.riv.global.result.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ssu.riv.global.result.ResultCode;

@Getter
@RequiredArgsConstructor
public enum RivResultCode implements ResultCode {
    ADD_Server(200, "SR000", "성공적으로 서버를 추가하였습니다."),
    ADD_Channel(200, "SR001", "성공적으로 채널을 추가하였습니다."),
    ;
    private final int status;
    private final String code;
    private final String message;
}
