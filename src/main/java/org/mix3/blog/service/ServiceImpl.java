package org.mix3.blog.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java.ao.DBParam;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.db.C3P0PoolProvider;

import org.mix3.blog.entity.Article;
import org.mix3.blog.entity.ArticleToCategory;
import org.mix3.blog.entity.Category;
import org.mix3.blog.entity.FreeContents;
import org.mix3.blog.entity.Image;
import org.mix3.blog.entity.Response;
import org.mix3.blog.entity.Setting;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.model.CategoryModel;
import org.mix3.blog.model.FreeContentsModel;
import org.mix3.blog.model.ImageModel;
import org.mix3.blog.model.ResponseModel;
import org.mix3.blog.model.SettingModel;
import org.mix3.blog.util.H2DatabaseProvider;
import org.mix3.blog.util.Utils;

import com.google.inject.Singleton;

@Singleton
public class ServiceImpl implements Service{
	private EntityManager em;
	
	public ServiceImpl(){
		Properties dbProperties = Utils.getDBProperties();
		String uri = dbProperties.getProperty("db.uri");
		String username = dbProperties.getProperty("db.username");
		String password = dbProperties.getProperty("db.password");
		
		em = new EntityManager(
				new C3P0PoolProvider(
						new H2DatabaseProvider(uri, username, password)));
		Logger.getLogger("net.java.ao").setLevel(Level.FINE);
	}
	
	public SettingModel getSetting() {
		return new SettingModel(em.get(Setting.class, 1));
	}
	
	public ImageModel getImage(int id) throws SQLException, IOException {
		return new ImageModel(em.get(Image.class, id));
	}
	
	public ArticleModel getArticle(int id) throws SQLException {
		return new ArticleModel(em.get(Article.class, id));
	}
	
	public List<ArticleModel> getArticleList(int limit, int offset) throws SQLException {
		List<ArticleModel> list = new ArrayList<ArticleModel>();
		for(Article a : em.find(Article.class, Query.select().order("id desc").limit(limit).offset(offset))){
			list.add(new ArticleModel(a));
		}
		return list;
	}
	
	public List<ArticleModel> getCategoryArticleList(String category, int limit, int offset) throws SQLException {
		List<ArticleModel> list = new ArrayList<ArticleModel>();
		for(ArticleToCategory atc : em.find(ArticleToCategory.class, Query.select("articletocategory.id").join(Category.class, "articletocategory.categoryid = category.id").where("category.name=?", category).group("articletocategory.articleid").limit(limit).offset(offset).order("articletocategory.articleid desc"))){
			list.add(new ArticleModel(atc.getArticle()));
		}
		return list;
	}
	
	public int getCountCategoryArticleList(String category) throws SQLException {
		return em.count(ArticleToCategory.class, Query.select().join(Category.class, "articletocategory.categoryid = category.id").where("name=?", category));
	}
	
	public List<CategoryModel> getCategoryList() throws SQLException {
		List<CategoryModel> categoryModelList = new ArrayList<CategoryModel>();
		for(Category c : em.find(Category.class, Query.select("category.id").join(ArticleToCategory.class, "category.id = articletocategory.categoryid").group("category.name"))){
			categoryModelList.add(new CategoryModel(c));
		}
		return categoryModelList;
	}
	
	public FreeContentsModel getFreeContents(int id) throws SQLException {
		return new FreeContentsModel(em.get(FreeContents.class, id));
	}
	
	public List<FreeContentsModel> getFreeContentsList() throws SQLException {
		List<FreeContentsModel> freeContentsModelList = new ArrayList<FreeContentsModel>();
		for(FreeContents fc : em.find(FreeContents.class, Query.select().order("priority"))){
			freeContentsModelList.add(new FreeContentsModel(fc));
		}
		return freeContentsModelList;
	}
	
	public void createResponse(ResponseModel responseModel) throws SQLException {
		Response response = em.create(Response.class, new DBParam[]{
			new DBParam("articleid", responseModel.getArticleid()),
			new DBParam("name", responseModel.getName()),
			new DBParam("contents", responseModel.getContents()),
			new DBParam("date", new Date(System.currentTimeMillis()))
		});
		response.save();
	}
}
