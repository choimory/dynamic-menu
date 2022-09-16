package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.controller.MenuController;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@Getter
public class ResponseMenuRegist extends RepresentationModel<ResponseMenuRegist> {
    private MenuDto registedMenu;

    @Builder
    public ResponseMenuRegist(MenuDto registedMenu) {
        this.registedMenu = registedMenu;
        add(linkTo(MenuController.class).slash(registedMenu.getId()).withSelfRel());
        add(linkTo(MenuController.class).slash(registedMenu.getId()).withRel("find"));
        add(linkTo(methodOn(MenuController.class).findAll(null, null)).withRel("find-all"));
        add(linkTo(MenuController.class).slash(registedMenu.getId()).withRel("update"));
        add(linkTo(MenuController.class).slash(registedMenu.getId()).withRel("delete"));
    }
}
