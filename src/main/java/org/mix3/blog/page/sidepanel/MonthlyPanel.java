package org.mix3.blog.page.sidepanel;

import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.frame.MyPanel;
import org.mix3.blog.page.ArchivePage;

@SuppressWarnings("serial")
public class MonthlyPanel extends MyPanel{
	public MonthlyPanel(String id) throws SQLException {
		super(id);
		ListView listView = new ListView("monthlyList", service.getMonthlyList()){
			@Override
			protected void populateItem(ListItem item) {
				final ArticleModel monthly = (ArticleModel)item.getModelObject();
				item.add(new BookmarkablePageLink("monthly", ArchivePage.class,
						new PageParameters("year="+monthly.getDateInfo("yyyy")+",month="+monthly.getDateInfo("MM"))){
					@Override
					protected void onComponentTagBody(
							MarkupStream markupStream, ComponentTag openTag) {
						replaceComponentTagBody(markupStream, openTag, monthly.getDateInfo("yyyy'年'MM'月'"));
					}
				});
			}
		};
		add(listView);
	}
}
