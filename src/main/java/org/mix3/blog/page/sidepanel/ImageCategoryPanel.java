package org.mix3.blog.page.sidepanel;

import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.mix3.blog.model.CategoryModel;
import org.mix3.blog.frame.MyPanel;
import org.mix3.blog.page.ImagePage;

@SuppressWarnings("serial")
public class ImageCategoryPanel extends MyPanel{
	public ImageCategoryPanel(String id) throws SQLException{
		super(id);
		ListView listView = new ListView("categorylist", service.getImageCategoryList()){
			@Override
			protected void populateItem(ListItem item) {
				final CategoryModel categoryModel = (CategoryModel)item.getModelObject();
				item.add(new BookmarkablePageLink("category", ImagePage.class, new PageParameters("category="+categoryModel.getName())){
					@Override
					protected void onComponentTagBody(
							MarkupStream markupStream, ComponentTag openTag) {
						replaceComponentTagBody(markupStream, openTag, categoryModel.getName()+"("+categoryModel.getImageNum()+")");
					}
				});
			}
		};
		add(listView);
	}
}
