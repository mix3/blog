package org.mix3.blog.auth.page;

import java.sql.Timestamp;
import java.util.Date;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.mix3.blog.auth.AuthenticatedWebPage;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.component.MyMultiLineLabel;
import org.mix3.blog.page.panel.CategoryPanel;

public class PreviewPage extends AuthenticatedWebPage{
	
	public PreviewPage(final CreatePage createPage, final ModalWindow window, final ArticleModel articleModel){
		articleModel.setDate(new Timestamp(new Date().getTime()));
		
		add(new Label("pre_date", new Model(articleModel.getDateInfo("yyyy'年'MM'月'dd'日'"))));
		add(new Label("pre_title", new Model(articleModel.getTitle())));
		add(new MyMultiLineLabel("pre_contents", articleModel.getContents()).setEscapeModelStrings(false));
		add(new Label("pre_time", new Model(articleModel.getDateInfo("hh:mm"))));
		add(new CategoryPanel("pre_categorypanel", articleModel.getCategory()));
	}
	
	public PreviewPage(IModel model){
		ArticleModel a = (ArticleModel)model.getObject();
		System.out.println(a.getTitle());
		System.out.println(a.getContents());

		a.setDate(new Timestamp(new Date().getTime()));
		add(new Label("date", new Model(a.getDateInfo("yyyy'年'MM'月'dd'日'"))));
		add(new Label("title1", new Model(a.getTitle())));
		add(new MyMultiLineLabel("contents", a.getContents()).setEscapeModelStrings(false));
		add(new Label("time", new Model(a.getDateInfo("hh:mm"))));
		add(new CategoryPanel("categorypanel", a.getCategory()));
	}
}
