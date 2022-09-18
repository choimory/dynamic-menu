package io.test.code.dynamicmenu.menu.dto.request;

import io.test.code.dynamicmenu.menu.entity.Menu;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;

@Builder(toBuilder = true)
@RequiredArgsConstructor
@Getter
public class RequestMenuUpdate {
    private final Long parentId;
    @NotEmpty
    private final String title;
    @NotEmpty
    private final String link;
    private final String description;

    public Menu toEntity(Integer calculatedDepth, Menu newParent){
        return Menu.builder()
                .title(title)
                .link(link)
                .depth(calculatedDepth)
                .description(description)
                .parent(parentId == null
                        ? null
                        : newParent)
                .build();
    }
}
