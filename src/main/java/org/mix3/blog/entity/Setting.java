package org.mix3.blog.entity;

import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.schema.SQLType;

public interface Setting extends Entity{
	public String getUid();
	public void setUid(String uid);
	
	public String getPass();
	public void setPass(String pass);
	
	public String getBlogname();
	public void setBlogname(String blogname);
	
	public String getDescription();
	public void setDescription(String description);
	
	@SQLType(Types.CLOB)
	public String getAbout();
	public void setAbout(String about);
	
	public String getBlogurl();
	public void setBlogurl(String blogurl);
	
	public int getListnum();
	public void setListnum(int listnum);
}
