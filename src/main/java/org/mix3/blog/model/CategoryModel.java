package org.mix3.blog.model;

import java.io.Serializable;

import org.mix3.blog.entity.Category;

@SuppressWarnings("serial")
public class CategoryModel implements Serializable{
	private Integer id;
	private String name;
	private int articleNum;
	private int imageNum;
	
	public CategoryModel(){}
	public CategoryModel(Category category){
		setId(category.getID());
		setName(category.getName());
		setArticleNum(category.getArticles().length);
		setImageNum(category.getImage().length);
	}
	public CategoryModel(String name) {
		setName(name);
	}
	public CategoryModel(int id, String name) {
		setId(id);
		setName(name);
	}
	public CategoryModel(int id, String name, int num) {
		setId(id);
		setName(name);
		setArticleNum(num);
		setImageNum(num);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getArticleNum() {
		return articleNum;
	}
	public void setArticleNum(int articleNum) {
		this.articleNum = articleNum;
	}
	public int getImageNum() {
		return imageNum;
	}
	public void setImageNum(int imageNum) {
		this.imageNum = imageNum;
	}
}
