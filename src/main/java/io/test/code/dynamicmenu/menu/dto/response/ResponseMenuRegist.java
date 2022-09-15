package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class ResponseMenuRegist extends RepresentationModel<ResponseMenuRegist> {
    private MenuDto registedMenu;

    @Builder
    public ResponseMenuRegist(MenuDto registedMenu) {
        this.registedMenu = registedMenu;
    }
}
