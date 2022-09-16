package io.test.code.dynamicmenu.menu.repository.querydsl;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;

import java.util.Optional;

public interface QMenuRepository {
    Optional<Integer> findDepthById(Long id);
    Boolean existByTitleAmongChild(String title, Long parentId);
}
