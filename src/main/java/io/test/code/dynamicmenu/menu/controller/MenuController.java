package io.test.code.dynamicmenu.menu.controller;

import io.test.code.dynamicmenu.menu.dto.response.*;
import io.test.code.dynamicmenu.menu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return null;
    }

    public ResponseMenuFindAll findAll(){
        return null;
    }

    public ResponseMenuRegist regist(){
        return null;
    }

    public ResponseMenuUpdate update(){
        return null;
    }

    public ResponseMenuDelete delete(){
        return null;
    }
}
