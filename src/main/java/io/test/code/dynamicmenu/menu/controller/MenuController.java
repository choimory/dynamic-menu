package io.test.code.dynamicmenu.menu.controller;

import io.test.code.dynamicmenu.common.dto.request.CommonPageRequest;
import io.test.code.dynamicmenu.menu.code.MenuDefaultSort;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuFindAll;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuRegist;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuUpdate;
import io.test.code.dynamicmenu.menu.dto.response.*;
import io.test.code.dynamicmenu.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/menu")
@RequiredArgsConstructor
@Validated
public class MenuController {
    private final MenuService menuService;

    @GetMapping("/{id}")
    public ResponseMenuFind find(@PathVariable
                                     @Valid
                                     @NotNull
                                     @Min(1) final Long id){
        return menuService.find(id);
    }

    @GetMapping("/search")
    public ResponseMenuFindAll findAll(final RequestMenuFindAll param,
                                       final CommonPageRequest pageRequest){
        return menuService.findAll(param,
                pageRequest.of(MenuDefaultSort.FIND_ALL.getProperty(), MenuDefaultSort.FIND_ALL.getDirection()));
    }

    @PostMapping
    public ResponseMenuRegist regist(@RequestBody @Valid final RequestMenuRegist param){
        return menuService.regist(param);
    }

    @PatchMapping("/{id}")
    public ResponseMenuUpdate update(@PathVariable
                                         @Valid
                                         @NotNull
                                         @Min(1) final Long id,
                                     @RequestBody
                                     @Valid final RequestMenuUpdate param){
        return menuService.update(id, param);
    }

    @DeleteMapping("/{id}")
    public ResponseMenuDelete delete(@PathVariable
                                         @Valid
                                         @NotNull
                                         @Min(1) final Long id){
        return menuService.delete(id);
    }
}
