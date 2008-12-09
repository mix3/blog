package org.mix3.blog.entity;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;

public interface Category extends Entity{
	public String getName();
	public void setName(String name);
	
	@ManyToMany(ArticleToCategory.class)
	public Article[] getArticles();
	
	@ManyToMany(ImageToCategory.class)
	public Image[] getImage();
}
