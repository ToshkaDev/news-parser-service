package com.vadim.parser;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface NewsDao extends CrudRepository<News, Long> {
	
	News findByLink(String link);
	
	List<News> findAllByOrderBySavedDateDesc(Pageable pageable);
	
	@SuppressWarnings("unchecked")
	News save(News news);

}
