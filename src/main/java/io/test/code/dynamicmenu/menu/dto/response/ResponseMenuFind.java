package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class ResponseMenuFind extends RepresentationModel<ResponseMenuFind> {
    private MenuDto menu;

    @Builder
    public ResponseMenuFind(MenuDto menu) {
        this.menu = menu;
    }
}
