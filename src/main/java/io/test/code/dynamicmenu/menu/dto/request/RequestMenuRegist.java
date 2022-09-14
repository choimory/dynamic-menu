package io.test.code.dynamicmenu.menu.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Builder(toBuilder = true)
@RequiredArgsConstructor
@Getter
public class RequestMenuRegist {
    private final Long parentId;
    @NotEmpty
    private final String title;
    @NotEmpty
    private final String link;
    private final String description;
}
