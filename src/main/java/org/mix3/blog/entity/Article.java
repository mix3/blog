package org.mix3.blog.entity;

import java.sql.Timestamp;
import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.OneToMany;
import net.java.ao.schema.SQLType;

public interface Article extends Entity{
	public String getTitle();
	public void setTitle(String title);
	
	@SQLType(Types.CLOB)
	public String getContents();
	public void setContents(String contents);
	
	@SQLType(Types.TIMESTAMP)
	public Timestamp getDate();
	public void setDate(Timestamp date);
	
	@OneToMany
	public Response[] getResponses();
	
	@OneToMany
	public Trackback[] getTrackbacks();
	
	@ManyToMany(ArticleToCategory.class)
	public Category[] getCategories();
}
