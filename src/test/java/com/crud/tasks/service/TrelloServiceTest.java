package com.crud.tasks.service;

import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.config.AdminConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;
    @Mock
    private TrelloClient trelloClient;
    @Mock
    private SimpleEmailService simpleEmailService;
    @Mock
    private AdminConfig adminConfig;

    @Test
    public void fetchTrelloBoardsTest() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("Piersza lista", "13", false));

        List<TrelloBoardDto> trelloBoardsList = new ArrayList<>();
        trelloBoardsList.add(new TrelloBoardDto("Pierwsza tablica", "14", trelloListDto));

        Mockito.when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardsList);
        //When
        List<TrelloBoardDto> boardDtoList = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(1, boardDtoList.size());
    }

    @Test
    public void createTrelloCardTest() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Pierwsza karta", "Opis karty", "top", "21");
        TrelloBadgesDto trelloBadge = new TrelloBadgesDto(112, new AttachmentByTypeDto());
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("14", "Pierwsza karta", "http://test.com", trelloBadge);

        Mockito.when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        Mockito.when(adminConfig.getAdminMail()).thenReturn("test@test.com");
        //When
        CreatedTrelloCardDto someCard = trelloService.createTrelloCard(trelloCardDto);

        //Then
        assertEquals("Pierwsza karta", someCard.getName());
    }
}