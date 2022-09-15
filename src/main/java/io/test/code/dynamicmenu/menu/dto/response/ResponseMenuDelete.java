package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class ResponseMenuDelete extends RepresentationModel<ResponseMenuDelete> {
    private MenuDto deletedMenu;

    @Builder
    public ResponseMenuDelete(MenuDto deletedMenu) {
        this.deletedMenu = deletedMenu;
    }
}
