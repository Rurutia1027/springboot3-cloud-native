package com.cloud.bookshop.web.controller;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
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
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenGetInfoFail() throws Exception {
        mockMvc.perform(get("/book/10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenGetInfoSuccess() throws Exception {
        mockMvc.perform(get("/book/item/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenCreateSuccess() throws Exception {
        mockMvc.perform(post("/book")
                        .with(csrf())
                        .content("{\"id\":null,\"name\":\"test_book\",\"content\":\"test_book\",\"publishDate\":\"2025-05-05\"}").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }


    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenUpdateSuccess() throws Exception {
        String content = "{\"id\":1,\"name\":\"test_book\",\"content\":\"test_book\",\"publishDate\":\"2025-05-05\"}";
        mockMvc.perform(put("/book/1")
                        .with(csrf())
                        .content(content).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/book/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // annotations for handling cookies or headers
    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void whenCookieOrHeaderExists() throws Exception {
        mockMvc.perform(get("/book/1")
                        .cookie(new Cookie("token", UUID.randomUUID().toString()))
                        .header("auth", UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    // upload file test case
    // disale this case to avoid generate test file
    // @Test
    public void whenUploadSuccess() throws Exception {
        // create a mock file to upload
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "multipart/form-data", "Hello, World".getBytes());

        // perform file upload
        String ret = mockMvc.perform(multipart("/file/upload")
                        .file(file))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        System.out.println(ret);
    }
}