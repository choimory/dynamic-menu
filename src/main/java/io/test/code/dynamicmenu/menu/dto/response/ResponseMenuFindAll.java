package io.test.code.dynamicmenu.menu.dto.response;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Getter
public class ResponseMenuFindAll extends RepresentationModel<ResponseMenuFindAll> {
    private int page;
    private int totalCount;
    private int totalPage;
    private int size;
    private String sort;
    private List<MenuDto> menus;

    @Builder
    public ResponseMenuFindAll(int page, int totalCount, int totalPage, int size, String sort, List<MenuDto> menus) {
        this.page = page;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.size = size;
        this.sort = sort;
        this.menus = menus;
    }
}
