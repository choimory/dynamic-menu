package io.test.code.dynamicmenu.common.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CommonResponse<T> {
    @Builder.Default
    private final int status = 0;
    private final String message;
    private final T data;
}
