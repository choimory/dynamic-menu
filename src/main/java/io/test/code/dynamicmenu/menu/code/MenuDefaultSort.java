package io.test.code.dynamicmenu.menu.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Getter
public enum MenuDefaultSort {
    FIND_ALL("id", Sort.Direction.ASC);

    private final String property;
    private final Sort.Direction direction;
}
