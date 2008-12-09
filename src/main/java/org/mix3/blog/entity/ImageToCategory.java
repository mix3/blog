package org.mix3.blog.entity;

import net.java.ao.Entity;

public interface ImageToCategory extends Entity{
	public Image getImage();
	public void setImage(Image image);
	
	public Category getCategory();
	public void setCategory(Category category);
}
