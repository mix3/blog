package org.mix3.blog.entity;

import java.sql.Timestamp;
import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.schema.SQLType;

public interface Trackback extends Entity{
	public String getTitle();
	public void setTitle(String title);
	public String getExcerpt();
	public void setExcerpt(String excerpt);
	public String getUrl();
	public void setUrl(String url);
	public String getBlogname();
	public void setBlogname(String blogname);
	
	@SQLType(Types.TIMESTAMP)
	public Timestamp getDate();
	public void setDate(Timestamp date);
	
	public Article getArticle();
	public void setArticle(Article article);
}
