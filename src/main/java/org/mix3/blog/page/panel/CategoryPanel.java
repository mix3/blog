package org.mix3.blog.page.panel;

import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.mix3.blog.component.MyBookmarkablePageLink;
import org.mix3.blog.frame.MyPanel;
import org.mix3.blog.model.CategoryModel;
import org.mix3.blog.page.CategoryPage;

@SuppressWarnings("serial")
public class CategoryPanel extends MyPanel{
	public CategoryPanel(String id, List<CategoryModel> categoryListModel) {
		super(id);
		ListView listView = new ListView("categoryList", categoryListModel){
			@Override
			protected void populateItem(ListItem item) {
				final CategoryModel categoryModel = (CategoryModel)item.getModelObject();
//				item.add(new Label("separate", "｜"));
				item.add(new MyBookmarkablePageLink("category", CategoryPage.class,
						new PageParameters("category="+categoryModel.getName()), categoryModel.getName()));
			}
		};
		add(listView);
	}

}
