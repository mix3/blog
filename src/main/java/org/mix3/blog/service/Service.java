package org.mix3.blog.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.model.CategoryModel;
import org.mix3.blog.model.FreeContentsModel;
import org.mix3.blog.model.ImageModel;
import org.mix3.blog.model.ResponseModel;
import org.mix3.blog.model.SettingModel;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceImpl.class)
public interface Service {
	public SettingModel getSetting();
	public ImageModel getImage(int id) throws SQLException, IOException;
	
	public ArticleModel getArticle(int id) throws SQLException;
	public List<ArticleModel> getArticleList(int limit, int offset) throws SQLException;
	public List<ArticleModel> getCategoryArticleList(String category, int limit, int offset) throws SQLException;
	public int getCountCategoryArticleList(String category) throws SQLException;
	public List<CategoryModel> getCategoryList() throws SQLException;
	public FreeContentsModel getFreeContents(int id) throws SQLException;
	public List<FreeContentsModel> getFreeContentsList() throws SQLException;
	
	public void createResponse(ResponseModel responseModel) throws SQLException;
}
