package org.mix3.blog.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.mix3.blog.entity.Category;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.model.CategoryModel;
import org.mix3.blog.model.FreeContentsModel;
import org.mix3.blog.model.ImageModel;
import org.mix3.blog.model.ResponseModel;
import org.mix3.blog.model.SettingModel;
import org.mix3.blog.model.TrackbackModel;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceImpl.class)
public interface Service {
	//read
	public ArticleModel getArticle(int id) throws SQLException;
	public int getCountArticleList() throws SQLException;
	public List<ArticleModel> getArticleList(int limit, int offset) throws SQLException;
	public List<ArticleModel> getArchiveArticleList(String year, String month) throws SQLException;
	public int getCountCategoryArticleList(String category) throws SQLException;
	public List<ArticleModel> getCategoryArticleList(String category, int limit, int offset) throws SQLException;
	public int getCountSearchArticleList(String search) throws SQLException;
	public List<ArticleModel> getSearchArticleList(String search, int limit, int offset) throws SQLException;
	public List<CategoryModel> getCategoryList() throws SQLException;
	public List<ArticleModel> getMonthlyList() throws SQLException;
	public ImageModel getImage(int id) throws SQLException, IOException;
	public List<ImageModel> getImageList(int limit, int offset) throws SQLException, IOException;
	public int getCountImageList() throws SQLException;
	public SettingModel getSetting();
	public List<FreeContentsModel> getFreeContentsList() throws SQLException;
	public List<FreeContentsModel> getFreeSideContentsList() throws SQLException;
	public FreeContentsModel getFreeContents(int id) throws SQLException;
	public FreeContentsModel getFreeSideContents(int id) throws SQLException;
	public List<CategoryModel> getImageCategoryList() throws SQLException;
	public List<ImageModel> getCategoryImageList(String category, int limit, int offset) throws SQLException;
	
	// Create
	public void createArticle(ArticleModel articleModel) throws SQLException;
	public Category createCategory(CategoryModel categoryModel) throws SQLException;
	public Category[] createCategoryList(List<CategoryModel> categoryModelList) throws SQLException;
	public void createResponse(ResponseModel responseModel) throws SQLException;
	public void createTrackback(TrackbackModel trackbackModel) throws SQLException;
	public void createImage(ImageModel imageModel) throws SQLException, IOException;
	public void createFreeContents(FreeContentsModel fcm) throws SQLException;
	public void createFreeSideContents(FreeContentsModel fcm) throws SQLException;
	
	// Update
	public void updateArticle(ArticleModel articleModel) throws SQLException;
	public void updateArticleList(List<ArticleModel> articleModelList) throws SQLException;
	public void updateSetting(SettingModel settingModel) throws SQLException;
	public void updateFreeContents(FreeContentsModel fcm) throws SQLException;
	public void updateFreeContentsList(List<FreeContentsModel> fcmList) throws SQLException;
	public void updateFreeSideContents(FreeContentsModel fcm) throws SQLException;
	public void updateFreeSideContentsList(List<FreeContentsModel> fcmList) throws SQLException;
	public void updateImageList(List<ImageModel> imageModelList) throws SQLException;
	
	// delete
	public void deleteArticle(ArticleModel articleModel) throws SQLException;
	public void deleteArticleList(List<ArticleModel> articleModelList) throws SQLException;
	public void deleteArticleToCategory(ArticleModel articleModel) throws SQLException;
	public void deleteImage(ImageModel imageModel) throws SQLException;
	public void deleteImageList(List<ImageModel> imageModelList) throws SQLException;
	public void deleteFreeContents(FreeContentsModel fcm) throws SQLException;
	public void deleteFreeSideContents(FreeContentsModel fcm) throws SQLException;
	public void deleteImageToCategory(ImageModel imageModel) throws SQLException;
	
	// send
	public void sendTrackback(String turl, ArticleModel articleModel) throws IOException, SQLException;
	
//	public SettingModel getSetting();
//	public ImageModel getImage(int id) throws SQLException, IOException;
//	
//	public ArticleModel getArticle(int id) throws SQLException;
//	public List<ArticleModel> getArticleList(int limit, int offset) throws SQLException;
//	public List<ArticleModel> getCategoryArticleList(String category, int limit, int offset) throws SQLException;
//	public int getCountCategoryArticleList(String category) throws SQLException;
//	public List<CategoryModel> getCategoryList() throws SQLException;
//	public FreeContentsModel getFreeContents(int id) throws SQLException;
//	public List<FreeContentsModel> getFreeContentsList() throws SQLException;
//	
//	public void createResponse(ResponseModel responseModel) throws SQLException;
}
