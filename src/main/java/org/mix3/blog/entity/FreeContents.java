package org.mix3.blog.entity;

import net.java.ao.Entity;

public interface FreeContents extends Entity{
	public int getPriority();
	public void setPriority(int priority);
	
	public String getTitle();
	public void setTitle(String title);
	
	public String getContents();
	public void setContents(String contents);
}
