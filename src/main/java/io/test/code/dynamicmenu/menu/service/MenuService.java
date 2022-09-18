package io.test.code.dynamicmenu.menu.service;

import io.test.code.dynamicmenu.common.exception.CommonException;
import io.test.code.dynamicmenu.menu.dto.dto.MenuDto;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuFindAll;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuRegist;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuUpdate;
import io.test.code.dynamicmenu.menu.dto.response.*;
import io.test.code.dynamicmenu.menu.entity.Menu;
import io.test.code.dynamicmenu.menu.repository.MenuRepository;
import io.test.code.dynamicmenu.menu.valid.MenuValid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MenuService {
    private final MenuRepository menuRepository;

    public ResponseMenuFind find(final Long id) {
        /*조회*/
        Menu menu = menuRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));

        /*반환*/
        return ResponseMenuFind.builder()
                .menu(MenuDto.toDto(menu))
                .build();
    }

    public ResponseMenuFindAll findAll(final RequestMenuFindAll param, Pageable pageable) {
        /*조회*/
        List<MenuDto> menus = menuRepository.findAllNoOffset(param.getLastId(), param.getParentId(), param.getDepth(), param.getTitle(), pageable.getPageSize());

        /*반환*/
        return ResponseMenuFindAll.builder()
                .size(pageable.getPageSize())
                .menus(menus)
                .build();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseMenuRegist regist(final RequestMenuRegist param) {
        /*자식간의 동일 메뉴명 중복 조회*/
        this.isMenuDuplicate(param.getTitle(), param.getParentId());

        /*뎁스 계산*/
        final int calculatedDepth = this.calculateDepth(param.getParentId());

        /*등록*/
        Menu registed = menuRepository.save(param.toEntity(calculatedDepth));

        /*반환*/
        return ResponseMenuRegist.builder()
                .registedMenu(MenuDto.toDto(registed))
                .build();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseMenuUpdate update(final Long id, final RequestMenuUpdate param) {
        /*대상 조회*/
        Menu updatedMenu = menuRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));

        /*자식간의 동일 메뉴명 중복 조회*/
        this.isMenuDuplicate(param.getTitle(), param.getParentId());

        /*뎁스 계산*/
        final int calculatedDepth = this.calculateDepth(param.getParentId());

        /*부모 조회*/
        final Menu newParent = param.getParentId() == null
                ? null
                : menuRepository.findById(param.getParentId())
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND,
                        MenuValid.CODE_PARENT_NOT_FOUND,
                        MenuValid.MESSAGE_PARENT_NOT_FOUND,
                        MenuValid.MESSAGE_PARENT_NOT_FOUND));

        /*수정*/
        updatedMenu.update(param.toEntity(calculatedDepth, newParent));

        /*반환*/
        return ResponseMenuUpdate.builder()
                .updatedMenu(MenuDto.toDto(updatedMenu))
                .build();
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public ResponseMenuDelete delete(final Long id) {
        /*조회*/
        Menu deletedMenu = menuRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND,
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        HttpStatus.NOT_FOUND.getReasonPhrase()));

        /*대상 및 하위 메뉴 일괄 삭제*/
        deletedMenu.delete();

        /*반환*/
        return ResponseMenuDelete.builder()
                .deletedMenu(MenuDto.toDto(deletedMenu))
                .build();
    }

    private void isMenuDuplicate(String title, Long parentId) throws CommonException {
        if (menuRepository.existByTitleAmongChild(title, parentId)) {
            throw new CommonException(HttpStatus.CONFLICT,
                    HttpStatus.CONFLICT.value(),
                    HttpStatus.CONFLICT.getReasonPhrase(),
                    HttpStatus.CONFLICT.getReasonPhrase());
        }
    }

    private Integer calculateDepth(Long parentId) throws CommonException {
        return parentId == null
                ? 1
                : menuRepository.findDepthById(parentId)
                .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND,
                        MenuValid.CODE_PARENT_NOT_FOUND,
                        MenuValid.MESSAGE_PARENT_NOT_FOUND,
                        MenuValid.MESSAGE_PARENT_NOT_FOUND)) + 1;
    }
}
