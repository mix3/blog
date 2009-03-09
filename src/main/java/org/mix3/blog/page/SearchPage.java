package org.mix3.blog.page;

import java.sql.SQLException;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.mix3.blog.model.ArticleModel;

public class SearchPage extends AbstractArticlePage{
	public SearchPage(PageParameters parameters) {
		super(parameters);

	}

	@Override
	protected List<ArticleModel> getArticleList(PageParameters parameters) throws SQLException {
		return service.getSearchArticleList(
				parameters.getString("word"), 
				service.getSetting().getListnum(),
				parameters.getInt("of", 0));
	}

	@Override
	protected int getSize(PageParameters parameters) throws SQLException {
		return service.getCountSearchArticleList(parameters.getString("word"));
	}
	
	@Override
	protected boolean isMyVisible() {
		return true;
	}
}
