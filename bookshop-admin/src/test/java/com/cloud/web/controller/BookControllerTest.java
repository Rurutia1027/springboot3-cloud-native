package com.cloud.web.controller;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
                        .cookie(new Cookie("token", UUID.randomUUID().toString()))
                        .header("auth", UUID.randomUUID().toString())
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

    @Test
    public void whenCreateSuccess() throws Exception {
        mockMvc.perform(post("/book").content("{\"id\":null,\"name\":\"test_book\",\"content\":\"test_book\",\"publishDate\":\"2025-05-05\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }


    @Test
    public void whenUpdateSuccess() throws Exception {
        String content = "{\"id\":1,\"name\":\"test_book\",\"content\":\"test_book\",\"publishDate\":\"2025-05-05\"}";
        mockMvc.perform(put("/book/1").content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/book/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // annotations for handling cookies or headers
    @Test
    public void whenCookieOrHeaderExists() throws Exception {
        mockMvc.perform(get("/book/1")
                        .cookie(new Cookie("token", UUID.randomUUID().toString()))
                        .header("auth", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}