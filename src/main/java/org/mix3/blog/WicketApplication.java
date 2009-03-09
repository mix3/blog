package org.mix3.blog;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Resource;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.Response;
import org.apache.wicket.Session;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.request.target.coding.MixedParamUrlCodingStrategy;
import org.apache.wicket.request.target.coding.QueryStringUrlCodingStrategy;
import org.apache.wicket.settings.IExceptionSettings;
import org.mix3.blog.auth.MyAuthorizationStrategy;
import org.mix3.blog.auth.MySession;
import org.mix3.blog.auth.page.Manage;
import org.mix3.blog.auth.page.PopupPage;
import org.mix3.blog.auth.page.UploadPage;
import org.mix3.blog.component.ImageUrlCodingStrategy;
import org.mix3.blog.component.ThumbnailUrlCodingStrategy;
import org.mix3.blog.error.ErrorPage;
import org.mix3.blog.error.ErrorRequestCycle;
import org.mix3.blog.page.ArchivePage;
import org.mix3.blog.page.CategoryPage;
import org.mix3.blog.page.DetailPage;
import org.mix3.blog.page.FreeContentsPage;
import org.mix3.blog.page.ImagePage;
import org.mix3.blog.page.SearchPage;
import org.mix3.blog.page.SignInPage;
import org.mix3.blog.page.TopPage;
import org.mix3.blog.page.TrackbackPage;
import org.mix3.blog.rss.MyFeedResource;
import org.mix3.blog.service.Module;
import org.mix3.blog.service.Service;

import com.google.inject.Guice;

public class WicketApplication extends WebApplication{
	@Override
	public Session newSession(Request request,
			org.apache.wicket.Response response) {
		return new MySession(request);
	}
	
	@Override
	public RequestCycle newRequestCycle(Request request, Response response) {
		return new ErrorRequestCycle(this, (WebRequest)request, (WebResponse)response);
	}
	
//	private void createDB(EntityManager em) throws SQLException, IOException, NoSuchAlgorithmException{
//		InputStream is = WicketApplication.class.getResourceAsStream("/db.sql");
//		StringBuffer out = new StringBuffer();
//		byte[] b = new byte[4096];
//		for (int n; (n = is.read(b)) != -1;) {
//			out.append(new String(b, 0, n));
//		}
//		
//		String[] sbuff = out.toString().split("\r\n\r\n");
//		
//		Connection conn = em.getProvider().getConnection();
//		Statement stmt = conn.createStatement();
//		for(String s : sbuff){
//			stmt.execute(s);
//		}
//		stmt.close();
//		conn.close();
//		
//		em.flushAll();
//		em.create(Setting.class, new DBParam[]{
//			new DBParam("uid", "admin"),
//			new DBParam("pass", Utils.digest("admin")),
//			new DBParam("blogname", "萌えキャラですね、わかります"),
//			new DBParam("description", "プログラミングとか自宅鯖管理などの備忘録(びぼうろく)＋絵うｐサイト"),
//			new DBParam("about", "細木和子に『しかし地獄行く』と宣告されて練炭自殺を何度も図るはた迷惑な人間"),
//			new DBParam("blogurl", "http://localhost:8080/"),
//			new DBParam("listnum", 10)
//		});
//	}
//	
//	private void generateSchema() throws NoSuchAlgorithmException, SQLException, IOException{
//		Properties dbProperties = Utils.getDBProperties();
//		String uri = dbProperties.getProperty("db.uri");
//		String username = dbProperties.getProperty("db.username");
//		String password = dbProperties.getProperty("db.password");
//		
//		EntityManager em = new EntityManager(
//				new C3P0PoolProvider(
//						new H2DatabaseProvider(uri, username, password)));
//		Logger.getLogger("net.java.ao").setLevel(Level.FINE);
//		
//		try{
//			if (em.count(Setting.class) == 0) {
//				System.out.println("debug:2");
//				em.create(Setting.class, new DBParam[]{
//					new DBParam("uid", "admin"),
//					new DBParam("pass", Utils.digest("admin")),
//					new DBParam("blogname", "萌えキャラですね、わかります"),
//					new DBParam("description", "プログラミングとか自宅鯖管理などの備忘録(びぼうろく)＋絵うｐサイト"),
//					new DBParam("about", "細木和子に『しかし地獄行く』と宣告されて練炭自殺を何度も図るはた迷惑な人間"),
//					new DBParam("blogurl", "http://localhost:8080/"),
//					new DBParam("listnum", 10)
//				});
//			}
//		}catch(Exception e){
//			createDB(em);
//		}
//		em.getProvider().getConnection().close();
//	}
	@SuppressWarnings("serial")
	@Override
	protected void init() {
//		try {
//			generateSchema();
//		} catch (NoSuchAlgorithmException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
		addComponentInstantiationListener(new GuiceComponentInjector(this));
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getSecuritySettings().setAuthorizationStrategy(new MyAuthorizationStrategy());
		getMarkupSettings().setStripWicketTags(true);
		getDebugSettings().setAjaxDebugModeEnabled(false);
		
		getApplicationSettings().setInternalErrorPage(ErrorPage.class);
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
		
		mountBookmarkablePage("/signin", SignInPage.class);
		mount(new ImageUrlCodingStrategy("/img"));
		mount(new ThumbnailUrlCodingStrategy("/img/thumbnail"));
		mountBookmarkablePage("/upload", UploadPage.class);
		mountBookmarkablePage("/popup", PopupPage.class);
		mountBookmarkablePage("/manage", Manage.class);
		
		mount(new QueryStringUrlCodingStrategy("/search/", SearchPage.class));
		mount(new MixedParamUrlCodingStrategy("/category", CategoryPage.class,  new String[] {"category"}));
		mount(new MixedParamUrlCodingStrategy("/archives", ArchivePage.class,  new String[] {"year","month"}));
		mount(new MixedParamUrlCodingStrategy("/detail", DetailPage.class,  new String[] {"id"}));
		mount(new MixedParamUrlCodingStrategy("/trackback", TrackbackPage.class,
				new String[] {"id","title","excerpt","url","blog_name"}));
		mountSharedResource("/rss", new ResourceReference("myFeed"){
			@Override
			protected Resource newResource() {
				return new MyFeedResource(Guice.createInjector(new Module()).getInstance(Service.class));
			}
		}.getSharedResourceKey());
		mountBookmarkablePage("/freecontents", FreeContentsPage.class);
		mount(new MixedParamUrlCodingStrategy("/image", ImagePage.class,  new String[] {"category"}));
	}
	

	public WicketApplication(){}
	public Class<? extends WebPage> getHomePage(){
		return TopPage.class;
	}
}