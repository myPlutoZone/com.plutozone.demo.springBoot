package com.plutozone.board.mapper;

import java.util.List;

import com.plutozone.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMpp {
	List<BoardDto> selectAll();

	void insert(BoardDto boardDto);
}
