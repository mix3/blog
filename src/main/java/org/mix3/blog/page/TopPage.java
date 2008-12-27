package org.mix3.blog.page;

import java.sql.SQLException;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.mix3.blog.model.ArticleModel;

public class TopPage extends AbstractArticlePage{
	public TopPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected List<ArticleModel> getArticleList(PageParameters parameters)
			throws SQLException {
		return service.getArticleList(service.getSetting().getListnum(), 0);
	}

	@Override
	protected int getSize(PageParameters parameters) throws SQLException {
		return 0;
	}

	@Override
	protected boolean isMyVisible() {
		return false;
	}
}
