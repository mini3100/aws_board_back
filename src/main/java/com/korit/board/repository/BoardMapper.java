package com.korit.board.repository;

import com.korit.board.entity.Board;
import com.korit.board.entity.BoardCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {
    public List<BoardCategory> getBoardCategories();
    public int saveCategory(BoardCategory boardCategory);
    public int saveBoard(Board board);
    public int usePoint(String email);
    public List<Board> getBoardList(Map<String, Object> paramsMap);
    public int getBoardCount(Map<String, Object> paramsMap);
    public Board getBoardByBoardId(int boardId);
    public int getLikeState(Map<String, Object> paramsMap);
    public int insertLike(Map<String, Object> paramsMap);
    public int deleteLike(Map<String, Object> paramsMap);
    public int deleteBoard(int boardId);
    public int editBoard(Board board);
}
