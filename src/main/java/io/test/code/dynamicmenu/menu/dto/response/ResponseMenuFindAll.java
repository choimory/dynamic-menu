package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.controller.MenuController;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuFindAll;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Getter
public class ResponseMenuFindAll extends RepresentationModel<ResponseMenuFindAll> {
    private Long lastId;
    private int size;
    private List<MenuDto> menus;

    @Builder
    public ResponseMenuFindAll(Long lastId, int size, List<MenuDto> menus) {
        this.lastId = lastId;
        this.size = size;
        this.menus = menus;
        //TODO 파라미터 동적 추가
        //add(Link.of("/menu/search{?title,parentId}").expand(Map.of("title","ttt","parentId",1L)));
        //add(Link.of("/menu/search{?title,parentId}").expand(Map.of("title","ttt")));
        //add(Link.of("/menu/search{?title,parentId}").expand(RequestMenuFindAll.builder().parentId(1L).title("ttt").build()));
        add(linkTo(methodOn(MenuController.class).findAll(RequestMenuFindAll.builder().title("ttt").build(), null)).withSelfRel().expand());
        add(linkTo(MenuController.class).slash("/id").withRel("find"));
    }
}
