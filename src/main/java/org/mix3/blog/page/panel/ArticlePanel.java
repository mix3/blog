package org.mix3.blog.page.panel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.mix3.blog.component.MyBookmarkablePageLink;
import org.mix3.blog.component.MyMultiLineLabel;
import org.mix3.blog.frame.MyPanel;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.page.DetailPage;

@SuppressWarnings("serial")
public class ArticlePanel extends MyPanel{
	public ArticlePanel(String id, IModel model){
		super(id, model);
		ArticleModel articleModel = (ArticleModel)model.getObject();
		add(new Label("date", new Model(articleModel.getDateInfo("yyyy'年'MM'月'dd'日'"))));
		add(new MyBookmarkablePageLink("title", DetailPage.class,
				new PageParameters("id="+articleModel.getId()), articleModel.getTitle()));
		add(new MyMultiLineLabel("contents", articleModel.getContents()).setEscapeModelStrings(false));
		add(new MyBookmarkablePageLink("time", DetailPage.class,
				new PageParameters("id="+articleModel.getId()), articleModel.getDateInfo("hh:mm")));
		add(new MyBookmarkablePageLink("comment", DetailPage.class,
				new PageParameters("id="+articleModel.getId()), "コメント("+articleModel.getResponses().size()+")")).setOutputMarkupId(true);
		add(new MyBookmarkablePageLink("trackback", DetailPage.class,
				new PageParameters("id="+articleModel.getId()), "トラックバック("+articleModel.getTrackback().size()+")")).setOutputMarkupId(true);
		add(new CategoryPanel("categorypanel", articleModel.getCategory()));
	}
	
	public ArticlePanel(String id, IModel model, String date) {
		super(id, model);
		ArticleModel articleModel = (ArticleModel)model.getObject();
		Label dateLabel = new Label("date", new Model(articleModel.getDateInfo("yyyy'年'MM'月'dd'日'")));
		if(date.equals(articleModel.getDateInfo("yyyyMMdd"))){
			dateLabel.setVisible(false);
		}
		add(dateLabel);
		add(new MyBookmarkablePageLink("title", DetailPage.class,
				new PageParameters("id="+articleModel.getId()), articleModel.getTitle()));
		add(new MyMultiLineLabel("contents", articleModel.getContents()).setEscapeModelStrings(false));
		add(new MyBookmarkablePageLink("time", DetailPage.class,
				new PageParameters("id="+articleModel.getId()), articleModel.getDateInfo("hh:mm")));
		add(new MyBookmarkablePageLink("comment", DetailPage.class,
				new PageParameters("id="+articleModel.getId()), "コメント("+articleModel.getResponses().size()+")")).setOutputMarkupId(true);
		add(new MyBookmarkablePageLink("trackback", DetailPage.class,
				new PageParameters("id="+articleModel.getId()), "トラックバック("+articleModel.getTrackback().size()+")")).setOutputMarkupId(true);
		add(new CategoryPanel("categorypanel", articleModel.getCategory()));
	}
}
