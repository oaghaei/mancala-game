package com.bol.mancala.api;

import com.bol.mancala.model.MancalaBoard;
import com.bol.mancala.service.MancalaGameService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
    void testCreateNewGame() throws Exception {

        mockMvc.perform(get("/create-new-game"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    void testPlayGame() throws Exception {
        MvcResult creationResult = mockMvc.perform(get("/create-new-game"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MancalaBoard actualMancalaBoard = objectMapper.readValue(creationResult.getResponse().getContentAsString(), MancalaBoard.class);

        MvcResult playResult = mockMvc.perform(put("/play/" + actualMancalaBoard.getGameId() + "/" + 1))
                .andExpect(status().isOk())
                .andReturn();
        MvcResult playResult2 = mockMvc.perform(put("/play/" + actualMancalaBoard.getGameId() + "/" + 3))
                .andExpect(status().isOk())
                .andReturn();
        System.out.println(objectMapper.readValue(playResult2.getResponse().getContentAsString(), MancalaBoard.class));
    }
}
