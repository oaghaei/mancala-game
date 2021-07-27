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

import static com.bol.mancala.constant.MancalaBoardTestCase.TEST_CASE_WINNER_1;
import static com.bol.mancala.constant.MancalaBoardTestCase.TEST_CASE_WINNER_2;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@DirtiesContext
class MancalaGameApiTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void playGameToEnd() throws Exception {
        MvcResult result = mockMvc.perform(put("/play/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MancalaBoardDto actualMancalaBoardDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        for (Integer pitId : TEST_CASE_WINNER_1) {
            result = mockMvc.perform(put("/play/" + pitId + "/" + actualMancalaBoardDto.getGameId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        }
        System.out.println(objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class));
    }

    @Test
    void playGameToEnd2() throws Exception {
        MvcResult result = mockMvc.perform(put("/play/8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MancalaBoardDto actualMancalaBoardDto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        for (Integer pitId : TEST_CASE_WINNER_2) {
            result = mockMvc.perform(put("/play/" + pitId + "/" + actualMancalaBoardDto.getGameId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        }
        System.out.println(objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class));
    }


}
