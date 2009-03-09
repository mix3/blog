package org.mix3.blog.auth.page;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.wicket.Page;
import org.apache.wicket.PageParameters;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.mix3.blog.auth.AuthenticatedWebPage;
import org.mix3.blog.model.ArticleModel;

public class CreatePage extends AuthenticatedWebPage{
	private ArticleModel articleModel = new ArticleModel();
	
	public CreatePage(PageParameters parameters){
		int id = parameters.getInt("id", 0);
		if(id != 0){
			try {
				articleModel = service.getArticle(id);
			} catch (SQLException e) {
				error("SQLException Error");
				e.printStackTrace();
			}
		}
		add(new CreateForm("form", new CompoundPropertyModel(articleModel)));
	}
	
	@SuppressWarnings("serial")
	public class CreateForm extends Form{
		String trackback = "";
		public CreateForm(String id, final IModel model) {
			super(id, model);
			add(new TextField("title").setRequired(true));
			add(new TextArea("contents").setRequired(true));
			add(new TextField("strcategory").setRequired(true));
			add(new TextArea("trackback", new PropertyModel(this, "trackback")));
			final ModalWindow modalWindow = new ModalWindow("modalWindow");
			add(modalWindow);
			modalWindow.setPageMapName("preview");
			modalWindow.setCookieName("preview");
			modalWindow.setPageCreator(new ModalWindow.PageCreator(){
				public Page createPage() {
					return new PreviewPage(CreatePage.this, modalWindow, articleModel);
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
						if(articleModel.getId() == null){
							service.createArticle(articleModel);
						}else{
							service.updateArticle(articleModel);
						}
						service.sendTrackback(trackback, articleModel);
						setResponsePage(Manage.class);
					} catch(NullPointerException e){
						error("NullPointerException Error");
						e.printStackTrace();
					} catch (SQLException e) {
						error("SQLException Error");
						e.printStackTrace();
					} catch (IOException e) {
						error("IOException Error");
						e.printStackTrace();
					}
				}
			});
		}
	}
}
