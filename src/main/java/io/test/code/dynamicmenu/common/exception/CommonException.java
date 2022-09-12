package io.test.code.dynamicmenu.common.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Getter
@RequiredArgsConstructor
public class CommonException extends RuntimeException {
    private final HttpStatus httpStatus;
    private final int status;
    private final String message;
}
