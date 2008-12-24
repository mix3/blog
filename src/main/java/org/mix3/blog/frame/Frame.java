package org.mix3.blog.frame;

import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.mix3.blog.WicketApplication;
import org.mix3.blog.model.SettingModel;
import org.mix3.blog.page.TopPage;
import org.mix3.blog.page.sidepanel.SidePanel;
import org.mix3.blog.service.Service;

import com.google.inject.Inject;

public class Frame extends AbstractFrame{
	@Inject
	protected Service service;
	private SettingModel settingModel;
	
	@SuppressWarnings("serial")
	public Frame() {
		settingModel = service.getSetting();
		
		add(HeaderContributor.forCss(WicketApplication.class, "resources/Styles/SyntaxHighlighter.css"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shCore.js"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shBrushPhp.js"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shBrushXml.js"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shBrushJava.js"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shBrushRuby.js"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shBrushJScript.js"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shBrushCpp.js"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shBrushCSharp.js"));
		add(HeaderContributor.forJavaScript(WicketApplication.class, "resources/Scripts/shBrushPerl.js"));
		
		add(new Label("header_title", settingModel.getBlogname()));
		add(new BookmarkablePageLink("title", TopPage.class){
			@Override
			protected void onComponentTagBody(MarkupStream markupStream,
					ComponentTag openTag) {
				replaceComponentTagBody(markupStream, openTag, settingModel.getBlogname());
			}
		});
		add(new Label("description", new Model(settingModel.getDescription())));
		
		add(new FeedbackPanel("feedback"));
		
//		add(new Label("sidebar", "sidebar"));
		try {
			add(new SidePanel("sidebar"));
		} catch (SQLException e) {
			throw new RuntimeErrorException(new Error("error"), "error_message");
		}
	}
}
