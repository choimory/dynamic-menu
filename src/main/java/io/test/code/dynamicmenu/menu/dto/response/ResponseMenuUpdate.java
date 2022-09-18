package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.controller.MenuController;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@NoArgsConstructor
@Getter
public class ResponseMenuUpdate extends RepresentationModel<ResponseMenuUpdate> {
    private MenuDto updatedMenu;

    @Builder
    public ResponseMenuUpdate(MenuDto updatedMenu) {
        this.updatedMenu = updatedMenu;
        add(linkTo(methodOn(MenuController.class).update(updatedMenu.getId(), null)).withSelfRel());
        add(linkTo(methodOn(MenuController.class).find(updatedMenu.getId())).withRel("find"));
        add(linkTo(methodOn(MenuController.class).delete(updatedMenu.getId())).withRel("delete"));
    }
}
