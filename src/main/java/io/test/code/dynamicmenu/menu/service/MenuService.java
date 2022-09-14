package io.test.code.dynamicmenu.menu.service;

import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuFindAll;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuRegist;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuUpdate;
import io.test.code.dynamicmenu.menu.dto.response.*;
import io.test.code.dynamicmenu.menu.entity.Menu;
import io.test.code.dynamicmenu.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    public ResponseMenuFind find(final Long id){
        Optional<Menu> menu = menuRepository.findById(id);
        return ResponseMenuFind.builder()
                .menu(MenuDto.toDto(menu.orElse(null)))
                .build();
    }

    public ResponseMenuFindAll findAll(final RequestMenuFindAll param, Pageable pageable){
        return null;
    }

    public ResponseMenuRegist regist(final RequestMenuRegist param){
        return null;
    }

    public ResponseMenuUpdate update(final RequestMenuUpdate param){
        return null;
    }

    public ResponseMenuDelete delete(final Long id){
        return null;
    }
}
