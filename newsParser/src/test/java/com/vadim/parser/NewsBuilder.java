package com.vadim.parser;

import java.util.Date;

public class NewsBuilder {
	
	private News news = new News();
	
	public NewsBuilder title (String title) {
		news.setTitle(title);
		return this;
	}

	public NewsBuilder link (String link) {
		news.setLink(link);
		return this;
	}
	
	public NewsBuilder date (Date date) {
		news.setSavedDate(date);
		return this;
	}
	
	public News build() {
		return news;
	}

}

