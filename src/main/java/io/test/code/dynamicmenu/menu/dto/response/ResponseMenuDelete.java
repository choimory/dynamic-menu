package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.controller.MenuController;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Getter
public class ResponseMenuDelete extends RepresentationModel<ResponseMenuDelete> {
    private MenuDto deletedMenu;

    @Builder
    public ResponseMenuDelete(MenuDto deletedMenu) {
        this.deletedMenu = deletedMenu;
        add(linkTo(MenuController.class).slash(deletedMenu.getId()).withSelfRel());
        add(linkTo(methodOn(MenuController.class).findAll(null, null)).withRel("find-all"));
    }
}
