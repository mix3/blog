package org.mix3.blog.component;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

@SuppressWarnings("serial")
public class MyBookmarkablePageLink extends BookmarkablePageLink{
	private String title;
	
	@Override
	protected void onComponentTagBody(MarkupStream markupStream,
			ComponentTag openTag) {
		// TODO 自動生成されたメソッド・スタブ
		replaceComponentTagBody(markupStream, openTag, this.title);
	}
	
	@SuppressWarnings("unchecked")
	public MyBookmarkablePageLink(String id, Class pageClass,
			PageParameters parameters, String title){
		super(id, pageClass, parameters);
		this.title = title;
	}
}
