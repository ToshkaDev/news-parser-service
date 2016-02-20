package com.vadim.parser;

import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.PageRequest;

@RunWith(MockitoJUnitRunner.class)
public class NewsParserApplicationTest {
	@InjectMocks
	private NewsController controller;
	@Mock
	private NewsDao newsDao;

	
	private static final String BOTH_TITLE_FIELD = "Гравитационные волны";
	private static final String FIRST_LINK_FIELD = "http://elementy.ru/novosti_nauki/432691/Gravitatsionnye_volny_otkryty";
	private static final String SECOND_LINK_FIELD = "http://www.computerra.ru/139927/advanced-ligo/";
	
	@SuppressWarnings("deprecation")
	private static final Date FIRST_DATE_FIELD = new Date (116, 1, 11, 10, 0, 0);
	@SuppressWarnings("deprecation")
	private static final Date SECOND_DATE_FIELD = new Date (115, 8, 14, 10, 0, 0);
	

	private static final News FIRST_NEWS = new NewsBuilder()
	    .title(BOTH_TITLE_FIELD)
	    .link(FIRST_LINK_FIELD)
	    .date(FIRST_DATE_FIELD)
	    .build();
	
	private static final News SECOND_NEWS = new NewsBuilder()
		.title(BOTH_TITLE_FIELD)
		.link(SECOND_LINK_FIELD)
		.date(SECOND_DATE_FIELD)
		.build();
	
	
	@Test
	public void whenFindingNewsItShouldReturnSpecNumbOfNews() {
	  // Given that the repository returns SECOND_NEWS and FIRST_NEWS
	  given(newsDao.findAllByOrderBySavedDateDesc(new PageRequest(0, 2))).willReturn(Arrays.asList(SECOND_NEWS, FIRST_NEWS));
	  // When looking for items
	  assertThat(controller.returnResults(2))
		  
	  // Then it should return the SECOND_NEWS and FIRST_NEWS
	  .containsOnly(SECOND_NEWS, FIRST_NEWS);
	}
	
}