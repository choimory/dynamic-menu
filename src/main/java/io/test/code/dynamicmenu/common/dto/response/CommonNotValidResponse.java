package io.test.code.dynamicmenu.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@RequiredArgsConstructor
@Getter
public class CommonNotValidResponse {
    private final String field;
    private final Object rejectedValue;
    private final String message;
}
