package io.test.code.dynamicmenu.menu.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
@Getter
public class RequestMenuFindAll {
    private final Long parentId;
    private final String depth;
    private final String title;
    private final String link;
    private final String description;
}
