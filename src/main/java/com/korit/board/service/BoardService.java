package com.korit.board.service;

import com.korit.board.aop.annotation.ArgsAop;
import com.korit.board.dto.*;
import com.korit.board.entity.Board;
import com.korit.board.entity.BoardCategory;
import com.korit.board.repository.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;

    public List<BoardCategoryRespDto> getBoardCategoriesAll() {
        List<BoardCategoryRespDto> boardCategoryRespDtos = new ArrayList<>();
        boardMapper.getBoardCategories().forEach(category -> {
            boardCategoryRespDtos.add(category.toCategoryDto());
        });
        return boardCategoryRespDtos;
    }

    @Transactional(rollbackFor = Exception.class)
    public boolean writeBoardContent(WriteBoardReqDto writeBoardReqDto) {
        BoardCategory boardCategory = null;
        if(writeBoardReqDto.getCategoryId() == 0) { // 새로 추가된 카테고리
            boardCategory = BoardCategory.builder()
                    .boardCategoryName(writeBoardReqDto.getCategoryName())
                    .build();
            boardMapper.saveCategory(boardCategory);
            writeBoardReqDto.setCategoryId(boardCategory.getBoardCategoryId());
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Board board = writeBoardReqDto.toBoardEntity(email);

        return boardMapper.saveBoard(board) > 0 && boardMapper.usePoint(email) > 0;
    }

    public List<BoardListRespDto> getBoardList(String categoryName, int page, SearchBoardListReqDto searchBoardListReqDto) {
        int index = (page - 1) * 10;
        Map<String, Object> paramsMap = new HashMap<>();

        paramsMap.put("index", index);
        paramsMap.put("categoryName", categoryName);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        List<BoardListRespDto> boardListRespDtos = new ArrayList<>();
        boardMapper.getBoardList(paramsMap).forEach(board -> {
            boardListRespDtos.add(board.toBoardListDto());
        });

        return boardListRespDtos;
    }

    public int getBoardCount(String categoryName, SearchBoardListReqDto searchBoardListReqDto) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("categoryName", categoryName);
        paramsMap.put("optionName", searchBoardListReqDto.getOptionName());
        paramsMap.put("searchValue", searchBoardListReqDto.getSearchValue());

        return boardMapper.getBoardCount(paramsMap);
    }

    public GetBoardRespDto getBoard(int boardId) {
        return boardMapper.getBoardByBoardId(boardId).toBoardDto();
    }

    public boolean getLikeState(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        System.out.println(paramsMap);
        return boardMapper.getLikeState(paramsMap) > 0;
    }

    public boolean setLike(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        return boardMapper.insertLike(paramsMap) > 0;
    }

    public boolean cancelLike(int boardId) {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("boardId", boardId);
        paramsMap.put("email", SecurityContextHolder.getContext().getAuthentication().getName());
        return boardMapper.deleteLike(paramsMap) > 0;
    }

    public boolean deleteBoard(int boardId) {
        return boardMapper.deleteBoard(boardId) > 0;
    }

    public boolean editBoard(EditBoardReqDto editBoardReqDto,int boardId) {
        BoardCategory boardCategory = null;
        if(editBoardReqDto.getCategoryId() == 0) { // 새로 추가된 카테고리
            boardCategory = BoardCategory.builder()
                    .boardCategoryName(editBoardReqDto.getCategoryName())
                    .build();
            boardMapper.saveCategory(boardCategory);
            editBoardReqDto.setCategoryId(boardCategory.getBoardCategoryId());
        }

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Board board = editBoardReqDto.toBoardEntity(email, boardId);
        return boardMapper.editBoard(board) > 0;
    }
}
