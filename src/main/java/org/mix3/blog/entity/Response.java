package org.mix3.blog.entity;

import java.sql.Timestamp;
import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.schema.SQLType;

public interface Response extends Entity{
	public String getName();
	public void setName(String name);
	
	@SQLType(Types.CLOB)
	public String getContents();
	public void setContents(String contents);
	
	@SQLType(Types.TIMESTAMP)
	public Timestamp getDate();
	public void setDate(Timestamp date);
	
	public Article getArticle();
	public void setArticle(Article article);
}
