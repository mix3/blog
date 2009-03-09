package org.mix3.blog.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.mix3.blog.entity.Article;
import org.mix3.blog.entity.Category;
import org.mix3.blog.entity.Response;
import org.mix3.blog.entity.Trackback;

@SuppressWarnings("serial")
public class ArticleModel implements Serializable{
	private Integer id;
	private String title;
	private String contents;
	private Timestamp date;
	private String strcategory;
	private String strtrackback;
	private List<CategoryModel> category;
	private List<ResponseModel> responses;
	private List<TrackbackModel> trackback;
	
	public ArticleModel(){}
	public ArticleModel(String title, String contents){
		setTitle(title);
		setContents(contents);
	}
	public ArticleModel(Article article){
		setId(article.getID());
		setTitle(article.getTitle());
		setContents(article.getContents());
		setDate(article.getDate());
		
		String strcategory = "";
		for(Category c : article.getCategories()){
			strcategory = strcategory + c.getName() + " ";
		}
		this.strcategory = strcategory;
		
		List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
		for(Category c : article.getCategories()){
			CategoryModel categoryModel = new CategoryModel(c);
			categoryModelList.add(categoryModel);
		}
		setCategory(categoryModelList);
		
		List<ResponseModel> responseModelList = new ArrayList<ResponseModel>();
		for(Response r : article.getResponses()){
			ResponseModel responseModel = new ResponseModel(r);
			responseModelList.add(responseModel);
		}
		setResponses(responseModelList);
		
		List<TrackbackModel> trackbackModelList = new ArrayList<TrackbackModel>();
		for(Trackback t : article.getTrackbacks()){
			TrackbackModel trackbackModel = new TrackbackModel(t);
			trackbackModelList.add(trackbackModel);
		}
		setTrackback(trackbackModelList);
	}
	public String getDateInfo(String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(this.date);
	}
//	public String getDateInfo(){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日'");
//		return sdf.format(date);
//	}
//	public String getMonthlyInfo(){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
//		return sdf.format(date);
//	}
//	public String getTimeInfo(){
//		SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
//		return sdf.format(date);
//	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
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
		String[] array = strcategory.split("\\p{Z}\\b");
		for(String s : array){
			categoryModelList.add(new CategoryModel(s));
		}
		this.category = categoryModelList;
	}
	public String getStrtrackback() {
		return strtrackback;
	}
	public void setStrtrackback(String strtrackback) {
		this.strtrackback = strtrackback;
	}
	public List<ResponseModel> getResponses() {
		return responses;
	}
	public void setResponses(List<ResponseModel> responses) {
		this.responses = responses;
	}
	public List<TrackbackModel> getTrackback() {
		return trackback;
	}
	public void setTrackback(List<TrackbackModel> trackback) {
		this.trackback = trackback;
	}
}
