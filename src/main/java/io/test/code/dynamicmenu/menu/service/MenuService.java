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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    public ResponseMenuFind find(final Long id){
        /*조회*/
        Menu menu = menuRepository.findById(id)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));

        /*반환*/
        return ResponseMenuFind.builder()
                .menu(MenuDto.toDto(menu))
                .build();
    }

    public ResponseMenuFindAll findAll(final RequestMenuFindAll param, Pageable pageable){
        /*조회*/
        /*변환*/
        /*반환*/

        return ResponseMenuFindAll.builder()
                .menus(null)
                .build();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseMenuRegist regist(final RequestMenuRegist param){
        /*중복조회 - 동일 뎁스의 동일 메뉴명*/
        //존재시 취소

        /*등록*/
        /*변환*/
        /*반환*/

        return null;
    }

    public ResponseMenuUpdate update(final RequestMenuUpdate param){
        /*수정*/
        /*변환*/
        /*반환*/
        return null;
    }

    public ResponseMenuDelete delete(final Long id){
        /*삭제*/
        /*반환*/
        return null;
    }
}
