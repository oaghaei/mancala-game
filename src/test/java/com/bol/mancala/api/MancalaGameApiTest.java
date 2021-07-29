package com.bol.mancala.api;

import com.bol.mancala.dto.MancalaBoardDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.stream.IntStream;

import static com.bol.mancala.constant.MancalaBoardTestCase.*;
import static com.bol.mancala.constant.MancalaConstant.LEFT_MAIN_PIT_ID;
import static com.bol.mancala.constant.MancalaConstant.RIGHT_MAIN_PIT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
    void startGameToEndToWinPlayerLeft() throws Exception {
        MvcResult result = mockMvc.perform(put("/play/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MancalaBoardDto dto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        for (Integer pitId : TEST_CASE_WINNER_1) {
            result = mockMvc.perform(put("/play/" + pitId + "/" + dto.getGameId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        }
        dto = objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        MancalaBoardDto expectedDto = objectMapper.readValue(readJsonCases(TEST_CASE_WINNER_1_EXPECTED_RESULT_JSON), MancalaBoardDto.class);
        expectedDto.setGameId(dto.getGameId());
        assertEquals(expectedDto, dto);
    }

    @Test
    void startGameToEndToWinPlayerRight() throws Exception {
        MvcResult result = mockMvc.perform(put("/play/8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        MancalaBoardDto dto =
                objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        for (Integer pitId : TEST_CASE_WINNER_2) {
            result = mockMvc.perform(put("/play/" + pitId + "/" + dto.getGameId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
        }
        dto = objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        MancalaBoardDto expectedDto = objectMapper.readValue(readJsonCases(TEST_CASE_WINNER_2_EXPECTED_RESULT_JSON), MancalaBoardDto.class);
        expectedDto.setGameId(dto.getGameId());
        assertEquals(expectedDto, dto);
    }

    @Test
    void playRandomGame() throws Exception {
        MancalaBoardDto dto = new MancalaBoardDto();
        int firstPit = selectRandomPit(dto);
        MvcResult result = mockMvc.perform(put("/play/" + selectRandomPit(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        dto = objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        System.out.println("/play/" + firstPit);
        while (!isGameFinished(dto)) {
            int randomPit = selectRandomPit(dto);
            System.out.println("/play/" + randomPit + "/" + dto.getGameId());
            result = mockMvc.perform(put("/play/" + randomPit + "/" + dto.getGameId()))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();
            dto = objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        }
        MancalaBoardDto finalDto = objectMapper.readValue(result.getResponse().getContentAsString(), MancalaBoardDto.class);
        assertEquals(72,
                dto.getPits().get(LEFT_MAIN_PIT_ID - 1).getStoneCount() + dto.getPits().get(RIGHT_MAIN_PIT_ID - 1).getStoneCount());
        assertTrue(IntStream.range(1, LEFT_MAIN_PIT_ID).allMatch(index -> finalDto.getPits().get(index - 1).getStoneCount() == 0));
        assertTrue(IntStream.range(LEFT_MAIN_PIT_ID + 1, RIGHT_MAIN_PIT_ID).allMatch(index -> finalDto.getPits().get(index - 1).getStoneCount() == 0));
    }

    private boolean isGameFinished(final MancalaBoardDto dto) {
        return IntStream.range(1, LEFT_MAIN_PIT_ID).allMatch(index -> dto.getPits().get(index - 1).getStoneCount() == 0) &&
                IntStream.range(LEFT_MAIN_PIT_ID + 1, RIGHT_MAIN_PIT_ID).allMatch(index -> dto.getPits().get(index - 1).getStoneCount() == 0);
    }

    private int selectRandomPit(final MancalaBoardDto dto) {
        int pitId = 1;
        if (dto.getPlayerTurn() == null)
            pitId = 1 + new Random().nextInt(13);
        else if (dto.getPlayerTurn() == 1)
            pitId = 1 + new Random().nextInt(6);
        else if (dto.getPlayerTurn() == 2)
            pitId = 7 + new Random().nextInt(7);
        if (pitId == 7 || (dto.getPits() != null && dto.getPits().get(pitId - 1).getStoneCount() == 0))
            return selectRandomPit(dto);
        return pitId;
    }

    private String readJsonCases(String filename) throws IOException {
        return StreamUtils.copyToString(new ClassPathResource(filename).getInputStream(), Charset.defaultCharset());
    }
}
