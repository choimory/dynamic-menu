package io.test.code.dynamicmenu.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.test.code.dynamicmenu.common.dto.response.CommonResponse;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuFindAll;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuRegist;
import io.test.code.dynamicmenu.menu.dto.request.RequestMenuUpdate;
import io.test.code.dynamicmenu.menu.dto.response.ResponseMenuRegist;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

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
class MenuControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    @DisplayName("단일조회_테스트")
    void find() throws Exception{
        /*given*/
        Long id = 1L;

        /*when*/
        ResultActions when = mockMvc.perform(get("/menu/{id}", id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());

        /*then*/
        when.andExpect(status().isOk())
                .andExpect(jsonPath("menu").exists())
                .andExpect(jsonPath("menu.id").value(id))
                .andExpect(jsonPath("_links").exists())
                .andExpect(jsonPath("_links.self.href", Matchers.endsWith(String.format("/menu/%d", id))))
                .andExpect(jsonPath("_links.update.href", Matchers.endsWith(String.format("/menu/%d", id))))
                .andExpect(jsonPath("_links.delete.href", Matchers.endsWith(String.format("/menu/%d", id))));
    }

    @Test
    void findAll() throws Exception {
        /*given*/
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        String page = "1";
        String size = "5";
        String sort = "id:asc";
        params.add("page", page);
        params.add("size", size);
        params.add("sort", sort);

        RequestMenuFindAll payload = RequestMenuFindAll.builder()
                .parentId(1L)
                .build();

        /*when*/
        ResultActions when = mockMvc.perform(post("/menu/search")
                .accept(APPLICATION_JSON)
                .contentType(APPLICATION_JSON)
                .params(params)
                .content(objectMapper.writeValueAsString(payload)))
                .andDo(print());

        /*then*/
        when.andExpect(status().isOk())
                .andExpect(jsonPath("page").value(page))
                .andExpect(jsonPath("size").value(size))
                .andExpect(jsonPath("sort").value(sort))
                .andExpect(jsonPath("_links.self.href", Matchers.endsWith("/menu/search")))
                .andExpect(jsonPath("_links.prev-page.href",Matchers.containsString("/menu/search")))
                .andExpect(jsonPath("_links.next-page.href", Matchers.containsString("/menu/search")));
    }

    @ParameterizedTest
    @MethodSource("sourceForRegist")
    void regist(RequestMenuRegist param, HttpStatus httpStatus) throws Exception {
        /*when*/
        ResultActions when = mockMvc.perform(post("/menu")
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
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
                        .andExpect(jsonPath("_links.self.href").value("/menu"))
                        .andExpect(jsonPath("_links.find.href").value(String.format("/menu/%d", result.getRegistedMenu().getId())))
                        .andExpect(jsonPath("_links.find-all.href").value("/menu/search"))
                        .andExpect(jsonPath("_links.update.href").value(String.format("/menu/%d", result.getRegistedMenu().getId())))
                        .andExpect(jsonPath("_links.delete.href").value(String.format("/menu/%d", result.getRegistedMenu().getId())));
                break;
            case BAD_REQUEST:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("data.field").isNotEmpty())
                        .andExpect(jsonPath("data.rejectedValue").isNotEmpty())
                        .andExpect(jsonPath("data.message").isNotEmpty());
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

        /*then*/
        switch (httpStatus == null ? HttpStatus.OK : httpStatus){
            case OK:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("updatedMenu").isNotEmpty())
                        .andExpect(jsonPath("_links.self.href").value(String.format("/menu/%d", id)))
                        .andExpect(jsonPath("_links.find.href").value(String.format("/menu/%d", id)))
                        .andExpect(jsonPath("_links.delete.href").value(String.format("/menu/%d", id)));
                break;
            case BAD_REQUEST:
                when.andExpect(status().is(httpStatus.value()))
                        .andExpect(jsonPath("data.field").isNotEmpty())
                        .andExpect(jsonPath("data.rejectedValue").isNotEmpty())
                        .andExpect(jsonPath("data.message").isNotEmpty());
                break;
        }
    }

    @Test
    void delete() throws Exception {
        /*given*/
        Long id = 1L;

        /*when*/
        ResultActions when = mockMvc.perform(MockMvcRequestBuilders.delete("/menu/{id}", id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON))
                .andDo(print());

        /*then*/
        when.andExpect(status().isOk())
                .andExpect(jsonPath("_links.self.href").value(String.format("/menu/%d", id)))
                .andExpect(jsonPath("_links.find-all.href", Matchers.endsWith("/menu/search")));
    }

    static Stream<Arguments> sourceForRegist(){
        return Stream.<Arguments>builder()
                //성공
                .add(Arguments.of(RequestMenuRegist.builder()
                                .parentId(1L)
                                .title("메뉴등록 테스트")
                                .link("naver.com")
                                .description("")
                                .build(),
                        HttpStatus.CREATED))
                //필수값 미입력
                .add(Arguments.of(RequestMenuRegist.builder()
                                .build(),
                        HttpStatus.BAD_REQUEST))
                .build();
    }

    static Stream<Arguments> sourceForUpdate(){
        return Stream.<Arguments>builder()
                //성공
                .add(Arguments.of(1L,
                        RequestMenuUpdate.builder()
                                .build(),
                        HttpStatus.OK))
                //잘못된 ID
                .add(Arguments.of(-1L,
                        RequestMenuUpdate.builder()
                                .build(),
                        HttpStatus.BAD_REQUEST))
                //필수값 미입력
                .add(Arguments.of(RequestMenuUpdate.builder()
                                .build(),
                        HttpStatus.BAD_REQUEST))
                .build();
    }
}