package org.mix3.blog.page;

import java.sql.SQLException;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.mix3.blog.model.ArticleModel;

public class CategoryPage extends AbstractArticlePage{
	public CategoryPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected List<ArticleModel> getArticleList(PageParameters parameters) throws SQLException {
		return service.getCategoryArticleList(
				parameters.getString("category"),
				service.getSetting().getListnum(),
				parameters.getInt("of", 0));
	}
	
	@Override
	protected int getSize(PageParameters parameters) throws SQLException {
		return service.getCountCategoryArticleList(parameters.getString("category"));
	}
	
	@Override
	protected boolean isMyVisible() {
		return true;
	}
}
