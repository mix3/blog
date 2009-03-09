package org.mix3.blog.page.panel;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.mix3.blog.component.tabpage.PageTab;
import org.mix3.blog.component.tabpage.TabbedPagePanel;
import org.mix3.blog.frame.MyPanel;
import org.mix3.blog.model.FreeContentsModel;
import org.mix3.blog.page.FreeContentsPage;
import org.mix3.blog.page.SignInPage;
import org.mix3.blog.page.TopPage;

@SuppressWarnings("serial")
public class MenuPanel extends MyPanel{
	public MenuPanel(String id) {
		super(id);
		List<PageTab> list = new ArrayList<PageTab>();
		try {
			list.add(new PageTab("Blog", TopPage.class, new PageParameters()));
			for(final FreeContentsModel fcm : service.getFreeContentsList()){
				list.add(new PageTab(fcm.getTitle(), FreeContentsPage.class, new PageParameters(new HashMap<String, String>(){{put("id", ""+fcm.getId());}})));
			}
			list.add(new PageTab("Login", SignInPage.class, new PageParameters()));
		} catch (SQLException e) {
			error("SQLException Error");
			e.printStackTrace();
		}
		add(new TabbedPagePanel("menu", list));
	}
}
