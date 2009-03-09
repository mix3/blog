package org.mix3.blog.auth.page;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.mix3.blog.auth.AuthenticatedWebPage;
import org.mix3.blog.model.FreeContentsModel;
import org.mix3.blog.component.MyBookmarkablePageLink;

public abstract class AbstractFreeContentsEditPage extends AuthenticatedWebPage{
	protected FreeContentsModel fcm = new FreeContentsModel();
	
	public AbstractFreeContentsEditPage(PageParameters parameters){
		try {
			add(new EditForm("freeContentsEditForm"));
		} catch (SQLException e) {
			error("SQLException Error");
			e.printStackTrace();
		}
		int id = parameters.getInt("id", 0);
		if(id != 0){
			try {
				fcm = getFreeContents(id);
			} catch (SQLException e) {
				error("SQLException Error");
				e.printStackTrace();
			}
		}
		add(new CreateForm("freeContentsCreateForm", new CompoundPropertyModel(fcm)));
	}
	
	@SuppressWarnings("serial")
	public class EditForm extends Form{
		public EditForm(String id) throws SQLException {
			super(id);
			final List<FreeContentsModel> list = getFreeContentsList();
			final ListView listView = new ListView("list", new Model((Serializable) list)){
				@SuppressWarnings("unchecked")
				@Override
				protected void populateItem(ListItem item) {
					final FreeContentsModel fcModel = (FreeContentsModel)item.getModelObject();
					item.add(new TextField("priority", new PropertyModel(fcModel, "priority")));
					item.add(new MyBookmarkablePageLink(
							"title", getCls(),
							new PageParameters(new HashMap(){{put("id", ""+fcModel.getId());}}),
							fcModel.getTitle()));
					item.add(new Button("delete"){
						@Override
						public void onSubmit() {
							try {
								deleteFreeContents(fcModel);
							} catch (SQLException e) {
								error("SQLException Error");
								e.printStackTrace();
							}
							setResponsePage(getCls());
						}
					});
				}
			};
			add(listView);
			add(new Button("update"){
				@Override
				public void onSubmit() {
					try {
						updateFreeContentsList(list);
					} catch (SQLException e) {
						error("SQLException Error");
						e.printStackTrace();
					}
					setResponsePage(getCls());
				}
			});
		}
	}
	
	@SuppressWarnings("serial")
	public class CreateForm extends Form{
		public CreateForm(String id, IModel model) {
			super(id, model);
			add(new TextField("priority"));
			add(new TextField("title"));
			add(new TextArea("contents"));
			final ModalWindow modalWindow = new ModalWindow("modalWindow");
			add(modalWindow);
			modalWindow.setPageMapName("preview");
			modalWindow.setCookieName("preview");
			modalWindow.setPageCreator(new ModalWindow.PageCreator(){
				public Page createPage() {
					return new PreviewFreeContentsPage(AbstractFreeContentsEditPage.this, modalWindow, fcm);
				}
			});
			modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback(){
				public void onClose(AjaxRequestTarget target) {
				}
			});
			modalWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback(){
				public boolean onCloseButtonClicked(AjaxRequestTarget arg0) {
					return true;
				}
			});
			add(new AjaxButton("preview"){
				@Override
				protected void onSubmit(AjaxRequestTarget target, Form form) {					// TODO 自動生成されたメソッド・スタブ
					modalWindow.show(target);
				}
			});
			add(new Button("submit"){
				@Override
				public void onSubmit() {
					try {
						if(fcm.getId() == 0){
							createFreeContents(fcm);
						}else{
							updateFreeContents(fcm);
						}
						setResponsePage(getCls());
					} catch (SQLException e) {
						error("SQLException Error");
						e.printStackTrace();
					}
				}
			});
		}
	}
	protected abstract FreeContentsModel getFreeContents(int id) throws SQLException;
	protected abstract List<FreeContentsModel> getFreeContentsList() throws SQLException;
	protected abstract void createFreeContents(FreeContentsModel fcm) throws SQLException;
	protected abstract void updateFreeContents(FreeContentsModel fcm) throws SQLException;
	protected abstract void updateFreeContentsList(List<FreeContentsModel> fcmList) throws SQLException;
	protected abstract void deleteFreeContents(FreeContentsModel fcm) throws SQLException;
	protected abstract Class<? extends AbstractFreeContentsEditPage> getCls();
}
