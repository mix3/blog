package org.mix3.blog;

import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.java.ao.DBParam;
import net.java.ao.EntityManager;
import net.java.ao.db.C3P0PoolProvider;

import org.apache.wicket.Request;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.Response;
import org.apache.wicket.guice.GuiceComponentInjector;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.settings.IExceptionSettings;
import org.mix3.blog.entity.Setting;
import org.mix3.blog.error.ErrorPage;
import org.mix3.blog.error.ErrorRequestCycle;
import org.mix3.blog.page.TopPage;
import org.mix3.blog.util.H2DatabaseProvider;
import org.mix3.blog.util.Utils;

public class WicketApplication extends WebApplication{
	@Override
	public RequestCycle newRequestCycle(Request request, Response response) {
		return new ErrorRequestCycle(this, (WebRequest)request, (WebResponse)response);
	}
	
	private void createDB(EntityManager em) throws SQLException, IOException, NoSuchAlgorithmException{
		InputStream is = WicketApplication.class.getResourceAsStream("/db.sql");
		StringBuffer out = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = is.read(b)) != -1;) {
			out.append(new String(b, 0, n));
		}
		
		String[] sbuff = out.toString().split("\r\n\r\n");
		
		Connection conn = em.getProvider().getConnection();
		Statement stmt = conn.createStatement();
		for(String s : sbuff){
			stmt.execute(s);
		}
		stmt.close();
		conn.close();
		
		em.flushAll();
		em.create(Setting.class, new DBParam[]{
			new DBParam("uid", "admin"),
			new DBParam("pass", Utils.digest("admin")),
			new DBParam("blogname", "萌えキャラですね、わかります"),
			new DBParam("description", "プログラミングとか自宅鯖管理などの備忘録(びぼうろく)＋絵うｐサイト"),
			new DBParam("about", "細木和子に『しかし地獄行く』と宣告されて練炭自殺を何度も図るはた迷惑な人間"),
			new DBParam("blogurl", "http://localhost:8080/"),
			new DBParam("listnum", 10)
		});
	}
	
	private void generateSchema() throws NoSuchAlgorithmException, SQLException, IOException{
		Properties dbProperties = Utils.getDBProperties();
		String uri = dbProperties.getProperty("db.uri");
		String username = dbProperties.getProperty("db.username");
		String password = dbProperties.getProperty("db.password");
		
		EntityManager em = new EntityManager(
				new C3P0PoolProvider(
						new H2DatabaseProvider(uri, username, password)));
		Logger.getLogger("net.java.ao").setLevel(Level.FINE);
		
		try{
			if (em.count(Setting.class) == 0) {
				System.out.println("debug:2");
				em.create(Setting.class, new DBParam[]{
					new DBParam("uid", "admin"),
					new DBParam("pass", Utils.digest("admin")),
					new DBParam("blogname", "萌えキャラですね、わかります"),
					new DBParam("description", "プログラミングとか自宅鯖管理などの備忘録(びぼうろく)＋絵うｐサイト"),
					new DBParam("about", "細木和子に『しかし地獄行く』と宣告されて練炭自殺を何度も図るはた迷惑な人間"),
					new DBParam("blogurl", "http://localhost:8080/"),
					new DBParam("listnum", 10)
				});
			}
		}catch(Exception e){
			createDB(em);
		}
		em.getProvider().getConnection().close();
	}
	@Override
	protected void init() {
		try {
			generateSchema();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addComponentInstantiationListener(new GuiceComponentInjector(this));
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		getMarkupSettings().setStripWicketTags(true);
		getDebugSettings().setAjaxDebugModeEnabled(false);
		
		getApplicationSettings().setInternalErrorPage(ErrorPage.class);
		getExceptionSettings().setUnexpectedExceptionDisplay(IExceptionSettings.SHOW_INTERNAL_ERROR_PAGE);
	}
	

	public WicketApplication(){}
	public Class<? extends WebPage> getHomePage(){
		return TopPage.class;
	}
}