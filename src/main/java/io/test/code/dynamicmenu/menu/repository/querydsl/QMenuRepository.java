package io.test.code.dynamicmenu.menu.repository.querydsl;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;

import java.util.List;
import java.util.Optional;

public interface QMenuRepository {
    Optional<Integer> findDepthById(Long id);
    Boolean existByTitleAmongChild(String title, Long parentId);
    List<MenuDto> findAllNoOffset(Long lastId, Long parentId, Integer depth, String title, int size);
}
