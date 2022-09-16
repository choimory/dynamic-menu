package io.test.code.dynamicmenu.menu.repository.querydsl;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import io.test.code.dynamicmenu.menu.entity.Menu;

import java.util.List;
import java.util.Optional;

public interface QMenuRepository {
    Optional<Integer> findDepthById(Long id);
    Boolean existByTitleAmongChild(String title, Long parentId);
    List<MenuDto> findAllNoOffset(Long lastId, Long parentId, String title, int size);
}
