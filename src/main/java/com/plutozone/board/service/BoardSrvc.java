package com.plutozone.board.service;

import java.util.List;

import com.plutozone.board.dto.BoardDto;
import com.plutozone.board.mapper.BoardMpp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardSrvc {
	
	@Autowired
	private BoardMpp boardMpp;
	
	public List<BoardDto> getAll(){
		return boardMpp.selectAll();
	}
	
	public void add(BoardDto post) {
		boardMpp.insert(post);
	}
}
