package org.setana.treenity.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // user
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저 정보를 찾을 수 없습니다."),
    POINT_NOT_ENOUGH(HttpStatus.NOT_ACCEPTABLE, "유저가 보유한 포인트가 부족합니다."),

    // tree
    TREE_NOT_FOUND(HttpStatus.NOT_FOUND, "나무를 찾을 수 없습니다."),
    LOCATION_NOT_VALID(HttpStatus.NOT_ACCEPTABLE, "주변의 나무와 가까워 해당 위치에 나무를 심을 수 없습니다."),

    // item
    ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "아이템 정보를 찾을 수 없습니다."),

    // user item
    USER_ITEM_NOT_FOUND(HttpStatus.NOT_FOUND, "유저의 아이템 보유 정보를 찾을 수 없습니다."),
    USER_ITEM_NOT_ENOUGH(HttpStatus.NOT_ACCEPTABLE, "유저가 보유한 아이템의 개수가 부족합니다."),
    USER_ITEM_LIMIT(HttpStatus.CONFLICT, "아이템 구매 개수 제한으로 아이템을 구매할 수 없습니다."),

    // user tree
    USER_TREE_DUPLICATE(HttpStatus.CONFLICT, "이미 유저가 등록한 북마크입니다."),

    // system
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "요청을 처리할 수 없습니다.");

    private final HttpStatus status;
    private final String message;

    ErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
