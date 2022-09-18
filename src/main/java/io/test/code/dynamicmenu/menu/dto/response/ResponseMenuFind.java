package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.controller.MenuController;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class ResponseMenuFind extends RepresentationModel<ResponseMenuFind> {
    private MenuDto menu;

    @Builder
    public ResponseMenuFind(MenuDto menu) {
        this.menu = menu;
        add(linkTo(MenuController.class).slash(menu.getId()).withSelfRel());
        add(linkTo(MenuController.class).slash(menu.getId()).withRel("update"));
        add(linkTo(MenuController.class).slash(menu.getId()).withRel("delete"));
    }
}
