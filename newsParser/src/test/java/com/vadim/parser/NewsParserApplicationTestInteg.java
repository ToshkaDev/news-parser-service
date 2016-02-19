package com.vadim.parser;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.apache.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jayway.restassured.RestAssured;
import static com.jayway.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = NewsParserApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class NewsParserApplicationTestInteg {
	private static final String TITLE_FIELD = "title";
	private static final String LINK_FIELD = "link";
	private static final String DATE_FIELD = "savedDate";
	
	private static final String BOTH_TITLE_FIELD = "Гравитационные волны";
	private static final String FIRST_LINK_FIELD = "http://elementy.ru/novosti_nauki/432691/Gravitatsionnye_volny_otkryty";
	private static final String SECOND_LINK_FIELD = "http://www.computerra.ru/139927/advanced-ligo/";
	
	@SuppressWarnings("deprecation")
	private static final Date FIRST_DATE_FIELD = new Date (116, 1, 11, 10, 0, 0);
	@SuppressWarnings("deprecation")
	private static final Date SECOND_DATE_FIELD = new Date (115, 8, 14, 10, 0, 0);
	
	private static final String NEWS_RESOURCE = "/showall/2";
	

	
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

	@Autowired
	private NewsDao newsDao;
	
	@Value("${local.server.port}")
	private int serverPort;
	
	private News firstNews;
	private News secondNews;

	@Before
	public void setUp() {
		newsDao.deleteAll();
		firstNews = newsDao.save(FIRST_NEWS);
		secondNews = newsDao.save(SECOND_NEWS);
		RestAssured.port = serverPort;
	}
	
	@Test
	public void getNewsShouldReturnBothNews() {
	  when()
	    .get(NEWS_RESOURCE)
	  .then()
	    .statusCode(HttpStatus.SC_OK)
	    .body(TITLE_FIELD, hasItems(BOTH_TITLE_FIELD, BOTH_TITLE_FIELD))
	    .body(LINK_FIELD, hasItems(FIRST_LINK_FIELD, SECOND_LINK_FIELD))
  	    .body(DATE_FIELD, hasItems(FIRST_DATE_FIELD.getTime(), SECOND_DATE_FIELD.getTime()));
	}
	
	@Test
	public void contextLoads() {
	}

}
