package io.test.code.dynamicmenu.menu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
    void find() throws Exception{
        /*given*/
        Long id = 1L;

        /*when*/
        ResultActions when = mockMvc.perform(get("/menu/{id}", id)
                .contentType(APPLICATION_JSON)
                .accept(APPLICATION_JSON));

        /*then*/
        when.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("menu").hasJsonPath())
                .andExpect(jsonPath("menu.id").value(id));
    }

    @Test
    void findAll() {
        /*given*/
        /*when*/
        /*then*/
    }

    @Test
    void regist() {
        /*given*/
        /*when*/
        /*then*/
    }

    @Test
    void update() {
        /*given*/
        /*when*/
        /*then*/
    }

    @Test
    void delete() {
        /*given*/
        /*when*/
        /*then*/
    }
}