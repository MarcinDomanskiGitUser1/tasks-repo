package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Matchers.any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;




import java.util.ArrayList;
import java.util.List;

@WebMvcTest(TrelloController.class)
@RunWith(SpringRunner.class)
public class TrelloControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) //or isOk()
                .andExpect(jsonPath("$", hasSize(0)));
    }
    @Test
    public void shouldFetchTrelloBoards() throws Exception {
        //Given
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("Test list", "1", false));
        List<TrelloBoardDto> trelloBoards = new ArrayList<>();
        trelloBoards.add(new TrelloBoardDto("Test task", "1", trelloLists));

        Mockito.when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoards);
        //When & Then
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200)) //or isOk()
                //Trello board fields
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$[0].name", Matchers.is("Test task")))
                //Trello list fields
                .andExpect(jsonPath("$[0].lists", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", Matchers.is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", Matchers.is("Test list")))
                .andExpect(jsonPath("$[0].lists[0].closed", Matchers.is(false)));
    }
    @Test
    public void shouldCreateTrelloCard() throws Exception {
    //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test", "Test description", "top", "1");
        TrelloBadgesDto trelloBadgesDto = new TrelloBadgesDto();
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("323", "Test", "http://test.com", trelloBadgesDto);

        Mockito.when(trelloFacade.createTrelloCardDto(Mockito.any(TrelloCardDto.class))).thenReturn(createdTrelloCardDto);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);
    //When & Then
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/trello/createTrelloCard")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))

                .andExpect(jsonPath("$.id", Matchers.is("323")))
                .andExpect(jsonPath("$.name", Matchers.is("Test")))
                .andExpect(jsonPath("$.shortUrl", Matchers.is("http://test.com")));
    }
}
