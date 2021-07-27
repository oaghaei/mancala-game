package com.bol.mancala.api;

import com.bol.mancala.dto.MancalaBoardDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext
class MancalaGameApiTest {

    private static final List<Integer> TEST_CASE =
            Arrays.asList(2, 8, 1, 13, 6, 13, 12, 4, 13, 12, 2, 8, 6, 5, 13, 11, 6, 10, 5, 13, 12, 4);

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testPlayGame() throws Exception {
        MvcResult result = mockMvc.perform(put("/play/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MancalaBoardDto actualMancalaBoardDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        for (Integer pitId : TEST_CASE) {
            result = mockMvc.perform(put("/play/" + pitId + "/" + actualMancalaBoardDto.getGameId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        }
        System.out.println(objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class));
    }
}
