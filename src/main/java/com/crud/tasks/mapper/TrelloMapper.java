package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;
import java.util.List;
import static java.util.stream.Collectors.toList;

@Component
public class TrelloMapper {

    public List<TrelloBoard> mapToBoards(final List<TrelloBoardDto> trelloBoardsDto) {
        return trelloBoardsDto.stream()
                .map(trelloBoardsDto1 -> new TrelloBoard(trelloBoardsDto1.getName(), trelloBoardsDto1.getId(), mapToList(trelloBoardsDto1.getLists())))
                .collect(toList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> trelloBoards) {
        return trelloBoards.stream()
                .map(trelloBoards1 -> new TrelloBoardDto(trelloBoards1.getName(), trelloBoards1.getId(), mapToListDto(trelloBoards1.getLists())))
                .collect(toList());
    }

    public List<TrelloList> mapToList(final List<TrelloListDto> trelloListDto) {
        return trelloListDto.stream()
                .map(trelloListDto1 -> new TrelloList(trelloListDto1.getId(), trelloListDto1.getName(), trelloListDto1.isClosed()))
                .collect(toList());
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> trelloList) {
        return trelloList.stream()
                .map(trelloList1 -> new TrelloListDto(trelloList1.getName(), trelloList1.getId(), trelloList1.isClosed()))
                .collect(toList());
    }

    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto) {
        return new TrelloCard(trelloCardDto.getName(), trelloCardDto.getDescription(), trelloCardDto.getPos(), trelloCardDto.getListId());
    }

    public TrelloCardDto mapToCardDto (final TrelloCard trelloCard) {
        return new TrelloCardDto(trelloCard.getName(), trelloCard.getDescription(), trelloCard.getPos(), trelloCard.getListId());
    }
}