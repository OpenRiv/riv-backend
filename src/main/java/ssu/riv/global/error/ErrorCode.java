package ssu.riv.global.error;


public interface ErrorCode {
    int getStatus();
    String getCode();
    String getMessage();
}
