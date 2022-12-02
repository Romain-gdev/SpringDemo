package com.example.springdemo

import com.example.springdemo.model.Message
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.*

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
class EntryPointsTests {
    @Autowired
    private lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var mapper: ObjectMapper

    @Test
    @Order(1)
    fun emptyMessages() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .get("/message")
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(
                MockMvcResultMatchers.content()
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.content().string("[]"))
            .andReturn()
    }

    @Test
    @Order(2)
    fun postMessageOK() {
        mockMvc.perform(
            MockMvcRequestBuilders
                .post("/message/message")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(Message("1", "text1")))
        ).andExpect(MockMvcResultMatchers.status().isCreated)
            .andExpect(
                MockMvcResultMatchers.content()
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
            )
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.text").value("text1"))
            .andReturn();
    }
}
