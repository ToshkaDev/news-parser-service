package com.vadim.parser;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NewsController{

	
	@RequestMapping(value="/showall/{resultNumber}", method=RequestMethod.GET, produces="application/json")
	public List<News> returnResults(@PathVariable(value="resultNumber") Integer resultNumber){
		return newsDao.findAllByOrderBySavedDateDesc(new PageRequest(0, resultNumber));
	}
	
	@Scheduled(cron="*/20 * * * * *")                                           //Do not forget to change !!!!
	public void save() throws Exception  {
		
		Pattern pattern = Pattern.compile("Java", Pattern.CASE_INSENSITIVE);
		Matcher matcher;
		
	    String title;
	    String newslink;
	    News news;
	    
	    String url = "http://www.novostiit.net/category/novosti";
	    Document document = Jsoup.connect(url).userAgent("Chrome").get();
	    Elements links = document.select(".post .ptitle a");
	    Element link;
	    
	    for (int i=0; i < 10; i++) {
	    	link = links.get(i);
			title = link.text();
			newslink = link.attr("href");
			
			if (newsDao.findByLink(newslink) == null) {
				news = new News(title, newslink);
				newsDao.save(news);
				
				matcher = pattern.matcher(title);
				if (matcher.find()) {
					smtpMailSender.send("funnydaisyk@gmail.com", "Java news", "Read Java news by this link "
				       + newslink);
				}
			}
	    }
	}

	@Autowired
	NewsDao newsDao;
	
	@Autowired
	SmtpMailSender smtpMailSender;
}
