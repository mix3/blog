package org.mix3.blog.page;

import java.sql.SQLException;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.mix3.blog.model.ArticleModel;

public class ArchivePage extends AbstractArticlePage{
	public ArchivePage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected List<ArticleModel> getArticleList(PageParameters parameters) throws SQLException {
		return service.getArchiveArticleList(
				parameters.getString("year"),
				parameters.getString("month"));
	}
	
	@Override
	protected boolean isMyVisible() {
		return false;
	}

	@Override
	protected int getSize(PageParameters parameters) throws SQLException {
		return 0;
	}
}
