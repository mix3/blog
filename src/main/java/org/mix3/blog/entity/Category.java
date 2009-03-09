package org.mix3.blog.entity;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.schema.Unique;

public interface Category extends Entity{
	@Unique
	public String getName();
	public void setName(String name);
	
	@ManyToMany(ArticleToCategory.class)
	public Article[] getArticles();
	
	@ManyToMany(ImageToCategory.class)
	public Image[] getImage();
}
