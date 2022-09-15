package io.test.code.dynamicmenu.menu.service;

import io.test.code.dynamicmenu.common.exception.CommonException;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuFindAll;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuRegist;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuUpdate;
import io.test.code.dynamicmenu.menu.dto.response.*;
import io.test.code.dynamicmenu.menu.entity.Menu;
import io.test.code.dynamicmenu.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    public ResponseMenuFind find(final Long id){
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new CommonException(HttpStatus.NO_CONTENT,
                        HttpStatus.NO_CONTENT.value(),
                        HttpStatus.NO_CONTENT.getReasonPhrase(),
                        HttpStatus.NO_CONTENT.getReasonPhrase()));

        return ResponseMenuFind.builder()
                .menu(MenuDto.toDto(menu))
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
