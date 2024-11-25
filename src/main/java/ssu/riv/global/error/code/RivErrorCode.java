package ssu.riv.global.error.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ssu.riv.global.error.ErrorCode;

@Getter
@RequiredArgsConstructor
public enum RivErrorCode implements ErrorCode {
    EXIST_SERVER(400, "ER000", "이미 추가한 서버입니다."),
    SERVER_NOT_FOUND(400, "ER001", "해당 서버가 존재하지 않습니다."),
    ;

    private final int status;
    private final String code;
    private final String message;
}
