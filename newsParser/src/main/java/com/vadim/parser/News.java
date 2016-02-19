package com.vadim.parser;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="newsTable")
public class News {
	
	@Id
	private String link;
	
	@NotNull
	private String title;
	
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date savedDate;
	
	public News() {
	}

	public News(String title, String link) {
		this.title = title;
		this.link = link;
	}


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Date getSavedDate() {
		return savedDate;
	}

	public void setSavedDate(Date savedDate) {
		this.savedDate = savedDate;
	}

	@Override
	public String toString() {
		return "News [title=" + title + ", link=" + link + ", savedDate="
				+ savedDate + "]";
	}
	

}
