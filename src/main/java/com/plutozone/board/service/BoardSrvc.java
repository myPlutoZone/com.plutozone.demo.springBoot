package com.plutozone.board;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {
	
	@Autowired
	private PostMapper postMapper;
	
	public List<Post> getAll(){
		return postMapper.selectAll();
	}
	
	public void add(Post post) {
		postMapper.insert(post);
	}
}
