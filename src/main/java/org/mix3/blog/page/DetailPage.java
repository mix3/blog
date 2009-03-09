package org.mix3.blog.page;

import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.string.StringValueConversionException;
import org.mix3.blog.model.ArticleModel;
import org.mix3.blog.model.ResponseModel;
import org.mix3.blog.model.TrackbackModel;
import org.mix3.blog.frame.Frame;
import org.mix3.blog.page.panel.ArticlePanel;

public class DetailPage extends Frame{
	private ArticleModel articleModel;
	
	@SuppressWarnings("serial")
	public DetailPage(PageParameters parameters) {
		try {
			articleModel = service.getArticle(parameters.getInt("id"));
		} catch (StringValueConversionException e) {
			error("StringValueConversionException Error");
		} catch (SQLException e) {
			error("SQLException Error");
		}
		add(new ArticlePanel("articlePanel", new Model(articleModel)));
		
		add(new TextField("trackbackurl", new Model(service.getSetting().getBlogurl()+"trackback/"+articleModel.getId()+"/")));
		ListView tlistView = new ListView("trackbacklist", articleModel.getTrackback()){
			@Override
			protected void populateItem(ListItem item) {
				TrackbackModel trackbackModel = (TrackbackModel)item.getModelObject();
				if(item.getIndex() % 2 == 0){
					item.add(new SimpleAttributeModifier("class","alt"));
				}
				item.add(new ExternalLink("title", trackbackModel.getUrl(), trackbackModel.getTitle()));
				item.add(new Label("blogname", new Model(trackbackModel.getBlogname())));
				item.add(new Label("date", new Model(trackbackModel.getDateInfo())));
				item.add(new Label("excerpt", new Model(trackbackModel.getExcerpt())));
			}
		};
		add(tlistView);
		
		ListView clistView = new ListView("commentlist", articleModel.getResponses()){
			@Override
			protected void populateItem(ListItem item) {
				ResponseModel responseModel = (ResponseModel)item.getModelObject();
				if(item.getIndex() % 2 == 0){
					item.add(new SimpleAttributeModifier("class","alt"));
				}
				item.add(new Label("date", new Model(responseModel.getDateInfo())));
				item.add(new Label("name", new Model(responseModel.getName())));
				item.add(new MultiLineLabel("contents", new Model(responseModel.getContents())));
			}
		};
		add(clistView);
		add(new CommentForm("form", articleModel.getId()));
	}
	
	@SuppressWarnings("serial")
	public class CommentForm extends Form{
		ResponseModel responseModel = new ResponseModel();
		public CommentForm(String id, final int articleid) {
			super(id);
			add(new TextField("name", new PropertyModel(responseModel, "name")).setRequired(true));
			add(new TextArea("comment", new PropertyModel(responseModel, "contents")).setRequired(true));
			add(new Button("submit"){
				@Override
				public void onSubmit() {
					responseModel.setArticleid(articleid);
					try {
						service.createResponse(responseModel);
					} catch (SQLException e) {
						error("SQLException Error");
					}
					setResponsePage(DetailPage.class, new PageParameters("id="+articleid));
				}
			});
		}
	}
}
