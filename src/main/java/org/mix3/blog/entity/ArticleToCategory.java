package org.mix3.blog.entity;

import net.java.ao.Entity;

public interface ArticleToCategory extends Entity{
	public Article getArticle();
	public void setArticle(Article article);
	
	public Category getCategory();
	public void setCategory(Category category);
}
