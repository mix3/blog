package org.mix3.blog.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.mix3.blog.entity.Trackback;

@SuppressWarnings("serial")
public class TrackbackModel implements Serializable{
	private Integer id;
	private Integer articleid;
	private String title;
	private String excerpt;
	private String url;
	private String blogname;
	private Timestamp date;
	
	public TrackbackModel(){}
	public TrackbackModel(Trackback trackback){
		setId(trackback.getID());
		setArticleid(trackback.getArticle().getID());
		setTitle(trackback.getTitle());
		setExcerpt(trackback.getExcerpt());
		setUrl(trackback.getUrl());
		setBlogname(trackback.getBlogname());
		setDate(trackback.getDate());
	}
	public TrackbackModel(int id, String title, String excerpt, String url, String blogname) {
		setArticleid(id);
		setTitle(title);
		setExcerpt(excerpt);
		setUrl(url);
		setBlogname(blogname);
	}
	
	public String getDateInfo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日' hh:mm");
		return sdf.format(this.date);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getArticleid() {
		return articleid;
	}
	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExcerpt() {
		return excerpt;
	}
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getBlogname() {
		return blogname;
	}
	public void setBlogname(String blogname) {
		this.blogname = blogname;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
}
