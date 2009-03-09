package org.mix3.blog.auth.page;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.mix3.blog.auth.AuthenticatedWebPage;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.component.LazyLoadingList;

public class EditPage extends AuthenticatedWebPage{
	public EditPage(){
		add(new EditForm("form"));
	}
	
	@SuppressWarnings("serial")
	public class EditForm extends Form{
		public EditForm(String id) {
			super(id);
			final CheckGroup checkgroup = new CheckGroup("checkgroup", new ArrayList<ArticleModel>());
			add(checkgroup);
			checkgroup.add(new CheckGroupSelector("checkall"));
			final List<ArticleModel> articleModelList = new LazyLoadingArticleList();
			PageableListView listView = new PageableListView("list", new Model((Serializable) articleModelList), 10){
				@Override
				protected void populateItem(ListItem item) {
					final ArticleModel a = (ArticleModel)item.getModelObject();
					item.add(new Check("check", item.getModel()));
					item.add(new BookmarkablePageLink("title", CreatePage.class, new PageParameters("id="+a.getId())){
						@Override
						protected void onComponentTagBody(
								MarkupStream markupStream, ComponentTag openTag) {
							replaceComponentTagBody(markupStream, openTag, a.getTitle());
						}
					});
					item.add(new TextField("category", new PropertyModel(a, "strcategory")));
					item.add(new Label("date", new PropertyModel(a, "date")));
					item.add(new Button("delete"){
						@Override
						public void onSubmit() {
							try {
								service.deleteArticle(a);
							} catch (SQLException e) {
								error("SQLException Error");
								e.printStackTrace();
							}
							setResponsePage(EditPage.class);
						}
					});
				}
			};
			checkgroup.add(new PagingNavigator("navi", listView));
			checkgroup.add(listView);
			checkgroup.add(new Button("deleteall"){
				@SuppressWarnings("unchecked")
				@Override
				public void onSubmit() {
					try {
						service.deleteArticleList((ArrayList<ArticleModel>)checkgroup.getModelObject());
					} catch (SQLException e) {
						error("SQLException Error");
						e.printStackTrace();
					}
					setResponsePage(EditPage.class);
				}
			});
			checkgroup.add(new Button("editall"){
				@Override
				public void onSubmit() {
					try {
						System.out.println("editall");
						service.updateArticleList(articleModelList);
					} catch (SQLException e) {
						error("SQLException Error");
						e.printStackTrace();
					}
					setResponsePage(EditPage.class);
				}
			});
		}
	}
	
	@SuppressWarnings("serial")
	public class LazyLoadingArticleList extends LazyLoadingList<ArticleModel>{
		@Override
		protected List<ArticleModel> getPage(int start, int size) throws SQLException {
			return service.getArticleList(size, start);
		}
		@Override
		public int size() {
			try {
				return service.getCountArticleList();
			} catch (SQLException e) {
				error("SQLException Error");
				e.printStackTrace();
				return 0;
			}
		}
	}
}
