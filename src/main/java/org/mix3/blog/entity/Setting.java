package org.mix3.blog.entity;

import net.java.ao.Entity;

public interface Setting extends Entity{
	public String getUid();
	public void setUid(String uid);
	
	public String getPass();
	public void setPass(String pass);
	
	public String getBlogname();
	public void setBlogname(String blogname);
	
	public String getDescription();
	public void setDescription(String description);
	
	public String getAbout();
	public void setAbout(String about);
	
	public String getBlogurl();
	public void setBlogurl(String blogurl);
	
	public int getListnum();
	public void setListnum(int listnum);
}
