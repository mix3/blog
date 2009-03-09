package org.mix3.blog.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java.ao.DBParam;
import net.java.ao.EntityManager;
import net.java.ao.Query;
import net.java.ao.Transaction;

import org.mix3.blog.entity.Article;
import org.mix3.blog.entity.ArticleToCategory;
import org.mix3.blog.entity.Category;
import org.mix3.blog.entity.FreeContents;
import org.mix3.blog.entity.FreeSideContents;
import org.mix3.blog.entity.Image;
import org.mix3.blog.entity.ImageToCategory;
import org.mix3.blog.entity.Response;
import org.mix3.blog.entity.Setting;
import org.mix3.blog.entity.Trackback;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.model.CategoryModel;
import org.mix3.blog.model.FreeContentsModel;
import org.mix3.blog.model.ImageModel;
import org.mix3.blog.model.ResponseModel;
import org.mix3.blog.model.SettingModel;
import org.mix3.blog.model.TrackbackModel;
import org.mix3.blog.util.H2DatabaseProvider;
import org.mix3.blog.util.Utils;

import com.google.inject.Singleton;

@Singleton
public class ServiceImpl implements Service{
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public ServiceImpl() throws SQLException{
		Properties dbProperties = Utils.getDBProperties();
		String uri = dbProperties.getProperty("db.uri");
		String username = dbProperties.getProperty("db.username");
		String password = dbProperties.getProperty("db.password");
		
//		em = new EntityManager(
//				new C3P0PoolProvider(
//						new H2DatabaseProvider(uri, username, password)));
		em = new EntityManager(new H2DatabaseProvider(uri, username, password));
		
		Logger.getLogger("net.java.ao").setLevel(Level.FINE);
		em.migrate(Setting.class,
				Trackback.class,
				Image.class,
				Category.class,
				ImageToCategory.class,
				Article.class,
				ArticleToCategory.class,
				FreeContents.class,
				FreeSideContents.class,
				Response.class);
		
		try {
			em.create(Setting.class, new DBParam[]{
				new DBParam("uid", "admin"),
				new DBParam("pass", Utils.digest("admin")),
				new DBParam("blogname", "萌えキャラですね、わかります"),
				new DBParam("description", "プログラミングとか自宅鯖管理などの備忘録(びぼうろく)＋絵うｐサイト"),
				new DBParam("about", "細木和子に『しかし地獄行く』と宣告されて練炭自殺を何度も図るはた迷惑な人間"),
				new DBParam("blogurl", "http://localhost:8080/"),
				new DBParam("listnum", 10)});
		} catch (NoSuchAlgorithmException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
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

	public void createArticle(final ArticleModel articleModel) throws SQLException {
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				Article a = em.create(Article.class, new DBParam[]{
					new DBParam("title", articleModel.getTitle()),
					new DBParam("contents", articleModel.getContents()),
					new DBParam("date", new Date(System.currentTimeMillis()))
				});
				a.save();
				
				Category[] categorylist = createCategoryList(articleModel.getCategory());
				for(Category c : categorylist){
					ArticleToCategory atc = em.create(ArticleToCategory.class, new DBParam[]{
						new DBParam("articleid", a.getID()),
						new DBParam("categoryid", c.getID()),
					});
					atc.save();
				}
				return null;
			}
		}.execute();
	}

	public Category createCategory(CategoryModel categoryModel)
			throws SQLException {
		Category category = null;
		Category[] categorylist = em.find(Category.class, Query.select().where("name=?", categoryModel.getName()));
		if(categorylist.length == 0){
			category = em.create(Category.class, new DBParam[]{
				new DBParam("name", categoryModel.getName())
			});
			category.save();
		}else{
			category = categorylist[0];
		}
		return category;
	}

	public Category[] createCategoryList(List<CategoryModel> categoryModelList)
			throws SQLException {
		List<Category> categorylist = new ArrayList<Category>();
		for(CategoryModel c : categoryModelList){
			Category category = createCategory(c);
			categorylist.add(category);
		}
		Set<Category> set = new HashSet<Category>(categorylist);
		return set.toArray(new Category[0]);
	}

	public void createFreeContents(FreeContentsModel fcm) throws SQLException {
		em.create(FreeContents.class, new DBParam[]{
			new DBParam("priority", fcm.getPriority()),
			new DBParam("title", fcm.getTitle()),
			new DBParam("contents", fcm.getContents())
		});
	}

	public void createFreeSideContents(FreeContentsModel fcm)
			throws SQLException {
		em.create(FreeSideContents.class, new DBParam[]{
			new DBParam("priority", fcm.getPriority()),
			new DBParam("title", fcm.getTitle()),
			new DBParam("contents", fcm.getContents())
		});
	}

	public void createImage(ImageModel imageModel) throws SQLException,
			IOException {
		if(imageModel.getThumbnail() != null){
			em.create(Image.class, new DBParam[]{
				new DBParam("name", imageModel.getName()),
				new DBParam("image", imageModel.getImage()),
				new DBParam("thumbnail", imageModel.getThumbnail()),
				new DBParam("date", new Date(System.currentTimeMillis()))
			});	
		}else{
			em.create(Image.class, new DBParam[]{
				new DBParam("name", imageModel.getName()),
				new DBParam("image", imageModel.getImage()),
				new DBParam("date", new Date(System.currentTimeMillis()))
			});
		}
	}

	public void createTrackback(TrackbackModel trackbackModel)
			throws SQLException {
		Trackback trackback = em.create(Trackback.class, new DBParam[]{
			new DBParam("articleid", trackbackModel.getArticleid()),
			new DBParam("title", trackbackModel.getTitle()),
			new DBParam("excerpt", trackbackModel.getExcerpt()),
			new DBParam("url", trackbackModel.getUrl()),
			new DBParam("blogname", trackbackModel.getBlogname()),
			new DBParam("date", new Date(System.currentTimeMillis()))
		});
		trackback.save();
	}

	public void deleteArticle(final ArticleModel articleModel) throws SQLException {
		new Transaction<Article>(em){
			@Override
			protected Article run() throws SQLException {
				em.delete(em.find(ArticleToCategory.class, Query.select().where("articleid=?", articleModel.getId())));
				em.delete(em.find(Trackback.class, Query.select().where("articleid=?", articleModel.getId())));
				em.delete(em.find(Response.class, Query.select().where("articleid=?", articleModel.getId())));
				em.delete(em.get(Article.class, articleModel.getId()));
				return null;
			}
		}.execute();
	}

	public void deleteArticleList(final List<ArticleModel> articleModelList)
			throws SQLException {
		new Transaction<Article>(em){
			@Override
			protected Article run() throws SQLException {
				for(ArticleModel a : articleModelList){
					em.delete(em.find(ArticleToCategory.class, Query.select().where("articleid=?", a.getId())));
					em.delete(em.find(Trackback.class, Query.select().where("articleid=?", a.getId())));
					em.delete(em.find(Response.class, Query.select().where("articleid=?", a.getId())));
					em.delete(em.get(Article.class, a.getId()));
				}
				return null;
			}
		}.execute();
	}

	public void deleteArticleToCategory(ArticleModel articleModel)
			throws SQLException {
		em.delete(em.find(ArticleToCategory.class, Query.select().where("articleid=?", articleModel.getId())));
	}

	public void deleteFreeContents(FreeContentsModel fcm) throws SQLException {
		em.delete(em.get(FreeContents.class, fcm.getId()));
	}

	public void deleteFreeSideContents(FreeContentsModel fcm)
			throws SQLException {
		em.delete(em.get(FreeSideContents.class, fcm.getId()));
	}

	public void deleteImage(ImageModel imageModel) throws SQLException {
		em.delete(em.find(Image.class, Query.select().where("id=?", imageModel.getId())));
	}

	public void deleteImageList(final List<ImageModel> imageModelList)
			throws SQLException {
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				for(ImageModel i : imageModelList){
					deleteImage(i);
				}
				return null;
			}
			
		}.execute();
	}

	public void deleteImageToCategory(ImageModel imageModel)
			throws SQLException {
		em.delete(em.find(ImageToCategory.class, Query.select().where("imageid=?", imageModel.getId())));
	}

	public List<ArticleModel> getArchiveArticleList(String year, String month)
			throws SQLException {
		List<ArticleModel> list = new ArrayList<ArticleModel>();
		for(Article a : em.find(Article.class, Query.select().order("id desc").where("FORMATDATETIME(date,'yyyyMM')=?", year+""+month))){
			list.add(new ArticleModel(a));
		}
		return list;
	}

	public List<ImageModel> getCategoryImageList(String category, int limit,
			int offset) throws SQLException {
		List<ImageModel> list = new ArrayList<ImageModel>();
		for(ImageToCategory itc : em.find(ImageToCategory.class, Query.select("imagetocategory.id").join(Category.class, "imagetocategory.categoryid = category.id").where("category.name=?", category).group("imagetocategory.imageid").limit(limit).offset(offset).order("imagetocategory.imageid desc"))){
			list.add(new ImageModel(itc.getImage()));
		}
		return list;
	}

	public int getCountArticleList() throws SQLException {
		return em.count(Article.class);
	}

	public int getCountImageList() throws SQLException {
		return em.count(Image.class);
	}

	public int getCountSearchArticleList(String search) throws SQLException {
		search = "%"+search+"%";
		return em.count(Article.class, Query.select().where("title like ? or contents like ?", search, search));
	}

	public FreeContentsModel getFreeSideContents(int id) throws SQLException {
		return new FreeContentsModel(em.get(FreeSideContents.class, id));
	}

	public List<FreeContentsModel> getFreeSideContentsList()
			throws SQLException {
		List<FreeContentsModel> freeContentsModelList = new ArrayList<FreeContentsModel>();
		for(FreeSideContents fc : em.find(FreeSideContents.class, Query.select().order("priority"))){
			freeContentsModelList.add(new FreeContentsModel(fc));
		}
		return freeContentsModelList;
	}

	public List<CategoryModel> getImageCategoryList() throws SQLException {
		List<CategoryModel> list = new ArrayList<CategoryModel>();
		for(Category c : em.find(Category.class, Query.select("category.id").join(ImageToCategory.class, "category.id = imagetocategory.categoryid").group("category.name"))){
			list.add(new CategoryModel(c));
		}
		return list;
	}

	public List<ImageModel> getImageList(int limit, int offset)
			throws SQLException, IOException {
		List<ImageModel> imageModel = new ArrayList<ImageModel>();
		for(Image i : em.find(Image.class, Query.select().order("id desc").limit(limit).offset(offset))){
			imageModel.add(new ImageModel(i));
		}
		return imageModel;
	}

	public List<ArticleModel> getMonthlyList() throws SQLException {
		List<ArticleModel> articleModelList = new ArrayList<ArticleModel>();
		String strdate = "";
		for(Article a : em.find(Article.class, Query.select().order("id desc"))){
			ArticleModel am = new ArticleModel(a);
			if(!strdate.equals(strdate = am.getDateInfo("yyyyMM"))){
				articleModelList.add(new ArticleModel(a));
			}
//			strdate = am.getDateInfo("yyyyMM");
//			System.out.println(""+a.getDate());
			
		}
		return articleModelList;
	}
	
	public List<ArticleModel> getSearchArticleList(String search, int limit,
			int offset) throws SQLException {
		search = "%"+search+"%";
		List<ArticleModel> list = new ArrayList<ArticleModel>();
		for(Article a : em.find(Article.class, Query.select().where("title like ? or contents like ?", search, search).limit(limit).offset(offset).order("id desc"))){
			list.add(new ArticleModel(a));
		}
		return list;
	}

	public void sendTrackback(String turl, ArticleModel articleModel)
			throws IOException, SQLException {
		if(turl == null){
			return;
		}
		String line = null;
		int id = 0;
		if(articleModel.getId() == null){
			Article a = Arrays.asList(em.find(Article.class, Query.select().order("id desc").limit(1))).get(0);
			id = a.getID();
		}else{
			id = articleModel.getId();
		}
		BufferedReader reader = new BufferedReader(new StringReader(turl));
		while((line = reader.readLine()) != null){
			String excerpt = articleModel.getContents();
			if(excerpt.length()> 60){
				excerpt = excerpt.substring(0, 60) + "...";
			}
			senderTrackback(line, articleModel.getTitle(), excerpt, getSetting().getBlogurl()+"detail/"+id+"/", getSetting().getBlogname());
		}
		reader.close();
	}
	
	private void senderTrackback(String turl, String title, String excerpt, String url, String blogName) throws IOException{
        URL connurl = new URL(turl);
        URLConnection uc = connurl.openConnection();
        uc.setDoOutput(true);
		OutputStreamWriter osw= new OutputStreamWriter(uc.getOutputStream());
		BufferedWriter bw = new BufferedWriter(osw);
		bw.write("title="+title+"&url="+url+"&blog_name="+blogName+"&excerpt="+excerpt);
		bw.close();
		osw.close();
		
		InputStreamReader isr = new InputStreamReader(uc.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		String line;
		while((line=br.readLine()) != null){
			System.out.println(line);
		}
		br.close();
		isr.close();
	}
	
	public void updateArticle(final ArticleModel articleModel) throws SQLException {
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				Article a = em.get(Article.class, articleModel.getId());
				a.setTitle(articleModel.getTitle());
				a.setContents(articleModel.getContents());
				a.save();
				
				deleteArticleToCategory(articleModel);
				Category[] categorylist = createCategoryList(articleModel.getCategory());
				for(Category c : categorylist){
					ArticleToCategory atc = em.create(ArticleToCategory.class, new DBParam[]{
						new DBParam("articleid", a.getID()),
						new DBParam("categoryid", c.getID()),
					});
					atc.save();
				}
				return null;
			}
		}.execute();
	}

	public void updateArticleList(final List<ArticleModel> articleModelList)
			throws SQLException {
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				for(ArticleModel articleModel : articleModelList){
					Article a = em.get(Article.class, articleModel.getId());
					
					if(compearCategoryList(a.getCategories(), articleModel.getCategory())){
						continue;
					}
					
//					a.setTitle(articleModel.getTitle());
//					a.setContents(articleModel.getContents());
//					a.save();
					
					deleteArticleToCategory(articleModel);
					Category[] categorylist = createCategoryList(articleModel.getCategory());
					for(Category c : categorylist){
						ArticleToCategory atc = em.create(ArticleToCategory.class, new DBParam[]{
							new DBParam("articleid", a.getID()),
							new DBParam("categoryid", c.getID()),
						});
						atc.save();
					}
				}
				return null;
			}
		}.execute();
	}
	
	private boolean compearCategoryList(Category[] categoryList, List<CategoryModel> categoryModelList){
		int length = categoryList.length;
		if (categoryModelList.size() != length) {
			System.out.println("length:false");
			return false;
		}
		for(int i=0; i<length; i++){
			if(!categoryModelList.get(i).getName().equals(categoryList[i].getName())){
				System.out.println(categoryModelList.get(i).getName()+":"+categoryList[i].getName());
				System.out.println("compear:false");
				return false;
			}
		}
		return true;
	}
	
	public void updateFreeContents(FreeContentsModel fcm) throws SQLException {
		FreeContents fc = em.get(FreeContents.class, fcm.getId());
		fc.setPriority(fcm.getPriority());
		fc.setTitle(fcm.getTitle());
		fc.setContents(fcm.getContents());
		fc.save();
	}

	public void updateFreeContentsList(final List<FreeContentsModel> fcmList)
			throws SQLException {
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				for(FreeContentsModel fcm : fcmList){
					updateFreeContents(fcm);
				}
				return null;
			}
		}.execute();
	}

	public void updateFreeSideContents(FreeContentsModel fcm)
			throws SQLException {
		FreeSideContents fc = em.get(FreeSideContents.class, fcm.getId());
		fc.setPriority(fcm.getPriority());
		fc.setTitle(fcm.getTitle());
		fc.setContents(fcm.getContents());
		fc.save();
	}

	public void updateFreeSideContentsList(final List<FreeContentsModel> fcmList)
			throws SQLException {
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				for(FreeContentsModel fcm : fcmList){
					updateFreeSideContents(fcm);
				}
				return null;
			}
		}.execute();
	}

	public void updateImageList(final List<ImageModel> imageModelList)
			throws SQLException {
		new Transaction<Object>(em){
			@Override
			protected Object run() throws SQLException {
				for(ImageModel imageModel : imageModelList){
					Image i = em.get(Image.class, imageModel.getId());
					
					if(compearCategoryList(i.getCategories(), imageModel.getCategory())){
						continue;
					}
					
					deleteImageToCategory(imageModel);
					Category[] categorylist = createCategoryList(imageModel.getCategory());
					for(Category c : categorylist){
						ImageToCategory itc = em.create(ImageToCategory.class, new DBParam[]{
							new DBParam("imageid", i.getID()),
							new DBParam("categoryid", c.getID()),
						});
						itc.save();
					}
				}
				return null;
			}
		}.execute();
	}

	public void updateSetting(SettingModel settingModel) throws SQLException {
		Setting s = em.get(Setting.class, 1);
		s.setBlogname(settingModel.getBlogname());
		s.setBlogurl(settingModel.getBlogurl());
		s.setDescription(settingModel.getDescription());
		s.setUid(settingModel.getUid());
		s.setPass(settingModel.getPass());
		s.setAbout(settingModel.getAbout());
		s.save();
	}
}
