package io.test.code.dynamicmenu.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.test.code.dynamicmenu.common.dto.response.CommonResponse;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuFindAll;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuRegist;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuUpdate;
import io.test.code.dynamicmenu.menu.dto.response.ResponseMenuRegist;
import io.test.code.dynamicmenu.menu.dto.response.ResponseMenuUpdate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@ActiveProfiles({"local"})
@Transactional
class MenuControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @ParameterizedTest
    @MethodSource("sourceForFind")
    @DisplayName("????????????_?????????")
    void find(Long id, HttpStatus httpStatus) throws Exception{
        /*when*/
        ResultActions when = mockMvc.perform(get("/menu/{id}", id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());

        /*then*/
        switch (httpStatus == null ? HttpStatus.OK : httpStatus){
            case OK:
                when.andExpect(status().isOk())
                        .andExpect(jsonPath("menu").exists())
                        .andExpect(jsonPath("menu.id").value(id))
                        .andExpect(jsonPath("_links").exists())
                        .andExpect(jsonPath("_links.self.href", Matchers.endsWith(String.format("/menu/%d", id))))
                        .andExpect(jsonPath("_links.update.href", Matchers.endsWith(String.format("/menu/%d", id))))
                        .andExpect(jsonPath("_links.delete.href", Matchers.endsWith(String.format("/menu/%d", id))));
                break;
            case NOT_FOUND:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("status").value(httpStatus.value()))
                        .andExpect(jsonPath("message").value(httpStatus.getReasonPhrase()));
                break;
            case BAD_REQUEST:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("data[0].field").exists())
                        .andExpect(jsonPath("data[0].rejectedValue").exists())
                        .andExpect(jsonPath("data[0].message").exists());
                break;
        }
    }

    @Test
    void findAll() throws Exception {
        /*given*/
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String page = "1";
        String size = "5";
        String sort = "id:asc";
        String parentId = "1";
        params.add("page", page);
        params.add("size", size);
        params.add("sort", sort);
        params.add("parentId", parentId);

        /*when*/
        ResultActions when = mockMvc.perform(get("/menu/search")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .params(params))
                .andDo(print());

        /*then*/
        when.andExpect(status().isOk())
                .andExpect(jsonPath("size").value(size))
                .andExpect(jsonPath("_links.self.href", Matchers.endsWith("/menu/search")));
    }

    @ParameterizedTest
    @MethodSource("sourceForRegist")
    void regist(RequestMenuRegist param, HttpStatus httpStatus) throws Exception {
        /*when*/
        ResultActions when = mockMvc.perform(post("/menu")
                .contentType(APPLICATION_JSON_UTF8)
                .accept(APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(param)))
                .andDo(print());

        ResponseMenuRegist result = objectMapper.readValue(when.andReturn()
                .getResponse()
                .getContentAsString(), ResponseMenuRegist.class);

        /*then*/
        switch (httpStatus == null ? HttpStatus.OK : httpStatus){
            case OK:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("registedMenu").isNotEmpty())
                        .andExpect(jsonPath("registedMenu.depth").value(result.getRegistedMenu().getParent().getDepth() + 1))
                        .andExpect(jsonPath("registedMenu.title").value(param.getTitle()))
                        .andExpect(jsonPath("registedMenu.link").value(param.getLink()))
                        .andExpect(jsonPath("registedMenu.description").value(param.getDescription()))
                        .andExpect(jsonPath("registedMenu.parent.id").value(param.getParentId()))
                        .andExpect(jsonPath("_links.self.href").value("/menu"))
                        .andExpect(jsonPath("_links.find.href").value(String.format("/menu/%d", result.getRegistedMenu().getId())))
                        .andExpect(jsonPath("_links.find-all.href").value("/menu/search"))
                        .andExpect(jsonPath("_links.update.href").value(String.format("/menu/%d", result.getRegistedMenu().getId())))
                        .andExpect(jsonPath("_links.delete.href").value(String.format("/menu/%d", result.getRegistedMenu().getId())));
                break;
            case BAD_REQUEST:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("data[0].field").isNotEmpty())
                        .andExpect(jsonPath("data[0].rejectedValue").hasJsonPath())
                        .andExpect(jsonPath("data[0].message").isNotEmpty());
                break;
            case CONFLICT:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("status").value(HttpStatus.CONFLICT.value()))
                        .andExpect(jsonPath("message").value(HttpStatus.CONFLICT.getReasonPhrase()))
                        .andExpect(jsonPath("data").value(HttpStatus.CONFLICT.getReasonPhrase()));
                break;
        }
    }

    @ParameterizedTest
    @MethodSource("sourceForUpdate")
    void update(Long id, RequestMenuUpdate param, HttpStatus httpStatus) throws Exception {
        /*when*/
        ResultActions when = mockMvc.perform(patch("/menu/{id}", id)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(param)))
                .andDo(print());

        ResponseMenuUpdate result = objectMapper.readValue(when.andReturn()
                .getResponse()
                .getContentAsString(), ResponseMenuUpdate.class);

        /*then*/
        switch (httpStatus == null ? HttpStatus.OK : httpStatus){
            case OK:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("updatedMenu").isNotEmpty())
                        .andExpect(jsonPath("updatedMenu.title").value(param.getTitle()))
                        .andExpect(jsonPath("updatedMenu.link").value(param.getLink()))
                        .andExpect(jsonPath("updatedMenu.description").value(param.getDescription()))
                        .andExpect(jsonPath("_links.self.href", Matchers.endsWith(String.format("/menu/%d", id))))
                        .andExpect(jsonPath("_links.find.href", Matchers.endsWith(String.format("/menu/%d", id))))
                        .andExpect(jsonPath("_links.delete.href", Matchers.endsWith(String.format("/menu/%d", id))));
                break;
            case BAD_REQUEST:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("data[0].field").isNotEmpty())
                        .andExpect(jsonPath("data[0].rejectedValue").hasJsonPath())
                        .andExpect(jsonPath("data[0].message").isNotEmpty());
                break;
        }
    }

    @ParameterizedTest
    @MethodSource("sourceForDelete")
    void delete(Long id, HttpStatus httpStatus) throws Exception {
        /*when*/
        ResultActions when = mockMvc.perform(MockMvcRequestBuilders.delete("/menu/{id}", id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());

        /*then*/
        switch (httpStatus == null ? HttpStatus.OK : httpStatus){
            case OK:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("_links.self.href", Matchers.endsWith(String.format("/menu/%d", id))))
                        .andExpect(jsonPath("_links.find-all.href", Matchers.endsWith("/menu/search")));
                break;
            case NOT_FOUND:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("status").value(httpStatus.value()))
                        .andExpect(jsonPath("message").value(httpStatus.getReasonPhrase()));;
                break;
        }
    }

    static Stream<Arguments> sourceForFind(){
        return Stream.<Arguments>builder()
                .add(Arguments.of(1L, HttpStatus.OK))
                .add(Arguments.of(-1L, HttpStatus.BAD_REQUEST))
                .add(Arguments.of(9999999999L, HttpStatus.NOT_FOUND))
                .build();
    }

    static Stream<Arguments> sourceForRegist(){
        return Stream.<Arguments>builder()
                //?????? ?????? ??????
                .add(Arguments.of(RequestMenuRegist.builder()
                                .title("????????? ??????")
                                .link("naver.com")
                                .description("?????????????????? ???????????????")
                                .banners(List.of(RequestMenuRegist.RequestBanner.builder()
                                        .title("????????? ??????")
                                        .link("naver.com")
                                        .imagePath("/foo/bar/abc.jpg")
                                        .imageHeight(123)
                                        .imageWidth(123)
                                        .imageSize(123)
                                        .build()))
                                .build(),
                        HttpStatus.CREATED))
                //?????? ?????? ??????
                .add(Arguments.of(RequestMenuRegist.builder()
                                .parentId(1L)
                                .title("????????? ??????")
                                .link("naver.com")
                                .description("?????????????????? ???????????????")
                                .banners(List.of(RequestMenuRegist.RequestBanner.builder()
                                        .title("????????? ??????")
                                        .link("naver.com")
                                        .imagePath("/foo/bar/abc.jpg")
                                        .imageHeight(123)
                                        .imageWidth(123)
                                        .imageSize(123)
                                        .build()))
                                .build(),
                        HttpStatus.CREATED))
                //?????? ????????? ??????
                .add(Arguments.of(RequestMenuRegist.builder()
                                .parentId(1L)
                                .title("????????? ??????")
                                .link("naver.com")
                                .description("?????????????????? ???????????????")
                                .banners(List.of(RequestMenuRegist.RequestBanner.builder()
                                        .imagePath("/foo/bar/abc.jpg")
                                        .imageHeight(123)
                                        .imageWidth(123)
                                        .imageSize(123)
                                        .build()))
                                .build(),
                        HttpStatus.BAD_REQUEST))
                //????????? ?????????
                .add(Arguments.of(RequestMenuRegist.builder()
                                .build(),
                        HttpStatus.BAD_REQUEST))
                //?????? ??????
                .add(Arguments.of(RequestMenuRegist.builder()
                                .title("??????-1")
                                .link("naver.com")
                                .description("?????????????????? ???????????????")
                                .build(),
                        HttpStatus.CONFLICT))
                //?????? ??????
                .add(Arguments.of(RequestMenuRegist.builder()
                                .parentId(1L)
                                .title("2???-1")
                                .link("naver.com")
                                .description("?????????????????? ???????????????")
                                .build(),
                        HttpStatus.CONFLICT))
                .build();
    }

    static Stream<Arguments> sourceForUpdate(){
        return Stream.<Arguments>builder()
                //??????
                .add(Arguments.of(1L,
                        RequestMenuUpdate.builder()
                                .title("abc")
                                .link("abc.com")
                                .description("??????")
                                .build(),
                        HttpStatus.OK))
                //????????? ID
                .add(Arguments.of(-1L,
                        RequestMenuUpdate.builder()
                                .build(),
                        HttpStatus.BAD_REQUEST))
                //?????? ID
                .add(Arguments.of(99999L,
                        RequestMenuUpdate.builder()
                                .build(),
                        HttpStatus.NOT_FOUND))
                //????????? ?????????
                .add(Arguments.of(1L,
                        RequestMenuUpdate.builder()
                                .build(),
                        HttpStatus.BAD_REQUEST))
                .build();
    }

    static Stream<Arguments> sourceForDelete(){
        return Stream.<Arguments>builder()
                //??????
                .add(Arguments.of(1L, HttpStatus.OK))
                //?????? ??????
                .add(Arguments.of(9999L, HttpStatus.NOT_FOUND))
                .build();
    }
}