package org.mix3.blog.rss;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.service.Service;
import org.wicketstuff.rome.FeedResource;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

@SuppressWarnings("serial")
public class MyFeedResource extends FeedResource{
	private Service service;
	
	public MyFeedResource(){}
	public MyFeedResource(Service service){
		this.service = service;
	}
	
	@Override
	protected SyndFeed getFeed() {
	    SyndFeed feed = new SyndFeedImpl();
	    feed.setFeedType("rss_2.0");
	    feed.setEncoding("UTF-8");
	    feed.setTitle(service.getSetting().getBlogname());
	    feed.setLink(service.getSetting().getBlogurl());
	    feed.setDescription(service.getSetting().getDescription());
	    feed.setPublishedDate(new Date());
	    
	    List<SyndEntry> entries = new ArrayList<SyndEntry>();
	    try {
			for(ArticleModel articleModel : service.getArticleList(service.getSetting().getListnum(), 0)){
				SyndEntry entry = new SyndEntryImpl();
			    entry.setTitle(articleModel.getTitle());
			    entry.setLink(service.getSetting().getBlogurl()+"detail/"+articleModel.getId()+"/");
			    entry.setPublishedDate(articleModel.getDate());
				SyndContent description = new SyndContentImpl();
				description.setType("text/plain");
				description.setValue(articleModel.getContents());
				entry.setDescription(description);
			    entries.add(entry);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	    feed.setEntries(entries);
	    return feed;
	}
}
