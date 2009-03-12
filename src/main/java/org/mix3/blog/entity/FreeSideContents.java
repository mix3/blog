package org.mix3.blog.entity;

import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.schema.SQLType;

public interface FreeSideContents extends Entity{
	public int getPriority();
	public void setPriority(int priority);
	
	public String getTitle();
	public void setTitle(String title);
	
	@SQLType(Types.CLOB)
	public String getContents();
	public void setContents(String contents);
}
