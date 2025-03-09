package com.cloud.web.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenBookQuerySuccess() throws Exception {
        mockMvc.perform(get("/book")
                        .param("name", "a and b")
                        .param("categoryId", "100")
                        .param("page", "1")
                        .param("size", "3")
                        .param("sort", "name,desc", "createdTime,asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3));
    }

    @Test
    public void whenGetInfoSuccess() throws Exception {
        String ret = mockMvc.perform(get("/book/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("mock_book_name"))
                .andReturn().getResponse().getContentAsString();
        System.out.println("return content " + ret);
    }


    @Test
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(get("/book/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }
}