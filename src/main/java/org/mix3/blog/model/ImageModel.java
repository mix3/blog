package org.mix3.blog.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.mix3.blog.entity.Category;
import org.mix3.blog.entity.Image;
import org.mix3.blog.util.Resize;
import org.mix3.blog.util.Utils;

@SuppressWarnings("serial")
public class ImageModel implements Serializable{
	private Integer id;
	private String name;
	private byte[] image;
	private byte[] thumbnail;
	private Timestamp date;
	private String strcategory;
	private List<CategoryModel> category;
	
	public ImageModel(){
	}
	public ImageModel(String name, InputStream inputStream) throws IOException {
//		Util util = new Util();
//		ChangeImage ci = new ChangeImage();
		Resize resize = new Resize();
		this.name = name;
		byte[] tmp = Utils.getBytes(inputStream);
		this.image = tmp;
		this.thumbnail = resize.resize(tmp.clone());
	}
	public ImageModel(Image image) {
		this.id = image.getID();
		this.name = image.getName();
		this.image = image.getImage();
		this.thumbnail = image.getThumbnail();
		this.date = image.getDate();
		String strcategory = "";
		for(Category c : image.getCategories()){
			strcategory = strcategory + c.getName() + " ";
		}
		this.strcategory = strcategory;
		
		List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
		for(Category c : image.getCategories()){
			CategoryModel categoryModel = new CategoryModel(c);
			categoryModelList.add(categoryModel);
		}
		setCategory(categoryModelList);
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
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public byte[] getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(byte[] thumbnail) {
		this.thumbnail = thumbnail;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
	public List<CategoryModel> getCategory() {
		return category;
	}
	public void setCategory(List<CategoryModel> category) {
		this.category = category;
	}
	public String getStrcategory() {
		return strcategory;
	}
	public void setStrcategory(String strcategory) {
		this.strcategory = strcategory;
		List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
		if(strcategory != null){
			String[] array = strcategory.split("\\p{Z}\\b");
			for(String s : array){
				categoryModelList.add(new CategoryModel(s));
			}
		}
		this.category = categoryModelList;
	}
}
