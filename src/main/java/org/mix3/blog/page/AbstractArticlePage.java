package org.mix3.blog.page;

import java.sql.SQLException;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.mix3.blog.frame.Frame;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.page.panel.ArticlePanel;
import org.mix3.blog.page.panel.NavigationPanel;

public abstract class AbstractArticlePage extends Frame{
	private String date = "";
	
	@SuppressWarnings("serial")
	public AbstractArticlePage(PageParameters parameters){
		try {
			add(getNavigationPanel(parameters));
			add(new ListView("articleList", getArticleList(parameters)){
				@Override
				protected void populateItem(ListItem item) {
					ArticleModel a = (ArticleModel)item.getModelObject();
					item.add(new ArticlePanel("articlePanel", new Model(a), date));
					date = a.getDateInfo("yyyyMMdd");
				}
			});
		} catch (SQLException e) {
			error("SQLException Error");
			e.printStackTrace();
		}
	}
	
	protected abstract List<ArticleModel> getArticleList(PageParameters parameters) throws SQLException;
	protected abstract int getSize(PageParameters parameters) throws SQLException;
	protected abstract boolean isMyVisible();
	
	protected NavigationPanel getNavigationPanel(PageParameters parameters) throws SQLException{
		return (NavigationPanel) new NavigationPanel("navi", getClass(), parameters, getSize(parameters), service.getSetting().getListnum()).setVisible(isMyVisible());
	}
}
