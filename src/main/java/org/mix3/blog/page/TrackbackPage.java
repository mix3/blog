package org.mix3.blog.page;

import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.protocol.http.WebRequest;
import org.mix3.blog.model.TrackbackModel;
import org.mix3.blog.service.Service;

import com.google.inject.Inject;

public class TrackbackPage extends WebPage{
	@Inject
	private Service service;
	
	private Integer id = null;
	private String title = null;
	private String excerpt = null;
	private String url = null;
	private String tblog_name = null;
	private String error = null;
	private String message = null;
	private Label messagelabel = null;
	private String method = null;
	
	@Override
	public String getMarkupType() {
		return "xml";
	}
	
	public TrackbackPage(PageParameters parameters){
		method = ((WebRequest)getRequestCycle().getRequest()).getHttpServletRequest().getMethod();
		id = parameters.getInt("id", -1);
		title = parameters.getString("title", "");
		excerpt = parameters.getString("excerpt", "");
		url = parameters.getString("url", "");
		tblog_name = parameters.getString("blog_name", "");
		
		if(id < 0 || title.equals("") || url.equals("") || method.equals("GET")){
			error = "1";
			message = "error";
			messagelabel = new Label("message", new Model(message));
		}else{
			error = "0";
			messagelabel = (Label) new Label("message", new Model("")).setVisible(false);
			try {
				service.createTrackback(new TrackbackModel(id, title, excerpt, url, tblog_name));
			} catch (SQLException e) {
				message = "error";
				messagelabel = new Label("message", new Model(message));
				e.printStackTrace();
			}
		}
		add(new Label("error", new Model(error)));
		add(messagelabel);
	}
}
