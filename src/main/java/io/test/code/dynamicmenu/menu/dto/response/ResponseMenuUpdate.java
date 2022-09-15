package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class ResponseMenuUpdate extends RepresentationModel<ResponseMenuUpdate> {
    private MenuDto updatedMenu;

    @Builder
    public ResponseMenuUpdate(MenuDto updatedMenu) {
        this.updatedMenu = updatedMenu;
    }
}
