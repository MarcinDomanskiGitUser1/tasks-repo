package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTest {
    @Autowired
    private TrelloMapper trelloMapper;

    @Test
    public void mapToBoardsTest() {
        // Given
        List<TrelloListDto> listDto1 = new ArrayList<>();
        listDto1.add(new TrelloListDto("Pierwsza lista", "231", false));

        List<TrelloBoardDto> boardDto1 = new ArrayList<>();
        boardDto1.add(new TrelloBoardDto("Pierwsza tablica", "51", listDto1));
        // When
        List<TrelloBoard> board1 = trelloMapper.mapToBoards(boardDto1);
        // Then
        assertEquals(1, board1.size());
    }
    @Test
    public void mapToBoardsDtoTest() {
        // Given
        List<TrelloList> list2 = new ArrayList<>();
        list2.add(new TrelloList("Druga lista", "232", true));

        List<TrelloBoard> board2 = new ArrayList<>();
        board2.add(new TrelloBoard("Druga tablica", "52", list2));
        // When
        List<TrelloBoardDto> boardDto2 = trelloMapper.mapToBoardsDto(board2);
        // Then
        assertEquals(1, boardDto2.size());
    }
    @Test
    public void mapToListTest() {
        // Given
        List<TrelloListDto> listDto3 = new ArrayList<>();
        listDto3.add(new TrelloListDto("Trzecia Lista", "53", false));
        // When
        List<TrelloList> list3 = trelloMapper.mapToList(listDto3);
        // Then
        assertEquals(1, list3.size());
    }
    @Test
    public void mapToListDtoTest() {
        // Given
        List<TrelloList> list4 = new ArrayList<>();
        list4.add(new TrelloList("Czwarta Lista", "54", true));
        // When
        List<TrelloListDto> listDto4 = trelloMapper.mapToListDto(list4);
        // Then
        assertEquals(1, listDto4.size());
    }
    @Test
    public void mapToCardTest() {
        // Given
        TrelloCardDto cardDto1 = new TrelloCardDto("Pierwsze zadanie", "Pierwszy Opis", "top", "test id");
        // When
        TrelloCard card1 = trelloMapper.mapToCard(cardDto1);
        // Then
        assertEquals("Pierwsze zadanie", card1.getName());
        assertEquals("Pierwszy Opis", card1.getDescription());
        assertEquals("top", card1.getPos());
        assertEquals("test id", card1.getListId());
    }
    @Test
    public void mapToCardDtoTest() {
        // Given
        TrelloCard card2 = new TrelloCard("Drugie zadanie", "Drugi Opis", "bottom", "test id");
        // When
        TrelloCardDto cardDto2 = trelloMapper.mapToCardDto(card2);
        // Then
        assertEquals("Drugie zadanie", cardDto2.getName());
        assertEquals("Drugi Opis", cardDto2.getDescription());
        assertEquals("bottom", cardDto2.getPos());
        assertEquals("test id", cardDto2.getListId());
    }
}
