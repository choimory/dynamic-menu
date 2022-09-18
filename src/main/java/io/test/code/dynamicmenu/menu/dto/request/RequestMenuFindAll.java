package io.test.code.dynamicmenu.menu.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder(toBuilder = true)
@RequiredArgsConstructor
@Getter
public class RequestMenuFindAll {
    private final Long lastId;
    private final Long parentId;
    private final Integer depth;
    private final String title;
}
