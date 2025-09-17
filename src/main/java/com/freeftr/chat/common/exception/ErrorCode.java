package com.freeftr.chat.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    MEMBER_NOT_FOUND(1000, "존재하지 않는 회원입니다."),
    NO_MEMBER_ID_REQUEST(1001, "요청에 멤버 아이디가 존재하지 않습니다."),

    WRONG_CHAT_ROOM_CAPACITY(2000, "채팅방 인원은 0보다 커야 합니다."),
    ;

    private final int code;
    private final String message;
}
