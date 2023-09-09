
package com.example.test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;

import com.example.test.model.Content;
import com.example.test.model.Type;
import com.example.test.model.Status;



@Repository 
public class ContentCollectionRepository {
    private final List<Content> contentList = new ArrayList<>();
    public ContentCollectionRepository() {
    }

    public List<Content> findAll() {
	return contentList;
    }

    public Optional<Content> findById(Integer id){
	return contentList.stream()
	    .filter(c -> c.id().equals(id))
	    .findFirst();
    }


    public void save(Content content) {
	contentList.removeIf(c -> c.id().equals(content.id()));
	contentList.add(content);
    }

    @PostConstruct
    private void init() {
	Content content = new Content(1,
				"My First Blog Post",
				"My First Blog Post",
				Status.IDEA,
				Type.ARTICLE,
				LocalDateTime.now(),
				null,
				"");

	contentList.add(content);
    }


    public boolean existsById(Integer id) {
	return contentList.stream()
	    .filter(c -> c.id().equals(id))
	    .count() == 1;
    }

    public void delete(Integer id) {
	contentList.removeIf(c -> c.id().equals(id));
    }
}
