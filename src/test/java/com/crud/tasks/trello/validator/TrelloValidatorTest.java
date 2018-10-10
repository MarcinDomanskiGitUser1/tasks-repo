package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloValidatorTest {
    @Autowired
    private TrelloValidator trelloValidator;

    @Test
    public void validateTrelloBoards() {
    //Given
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        List<TrelloList> trelloLists1 = new ArrayList<>();
        List<TrelloList> trelloLists2 = new ArrayList<>();
        trelloBoards.add(new TrelloBoard("150", "Test", trelloLists1));
        trelloBoards.add(new TrelloBoard("130", "testowa", trelloLists2));

     //When
        List<TrelloBoard> trelloBoardsFiltered = trelloValidator.validateTrelloBoards(trelloBoards);
     //Then
        assertEquals(1, trelloBoardsFiltered.size());
    }
}