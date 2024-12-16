package ssu.riv.global.result.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import ssu.riv.global.result.ResultCode;

@Getter
@RequiredArgsConstructor
public enum RivResultCode implements ResultCode {
    ADD_Server(200, "SR000", "성공적으로 서버를 추가하였습니다."),
    ADD_Channel(200, "SR001", "성공적으로 채널을 추가하였습니다."),
    GET_CHANNEL_ID(200, "SR002", "성공적으로 채널id를 조회했습니다."),
    GET_CHANNEL_LIST(200, "SR003", "성공적으로 채널 id 리스트를 조회했습니다."),
    GET_RECODING_LIST(200, "SR004", "성공적으로 해당 채널의 요약본 텍스트 목록을 조회했습니다."),
    SAVE_RECODING(200, "SR005", "성공적으로 요약본을 저장헀습니다."),
    RECODING_INFO(200, "SR006", "성공적으로 요약본을 조회헀습니다."),
    UPDATE_RECODING(200, "SR007", "성공적으로 요약본을 수정헀습니다."),
    DELETE_RECODING(200, "SR008", "성공적으로 요약본을 삭제헀습니다."),
    GET_SERVER_ID(200, "SR009", "성공적으로 서버id를 조회했습니다."),
    CATEGORY_LIST(200, "SR010", "성공적으로 카테고리 목록을을 조회했습니다."),
    CATEGORY_RECODING_INFO(200, "SR011", "성공적으로 해당 카테고리의 요약본 목록을 조회했습니다."),
    ;
    private final int status;
    private final String code;
    private final String message;
}
