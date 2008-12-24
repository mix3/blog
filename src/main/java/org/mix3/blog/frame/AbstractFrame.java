package org.mix3.blog.frame;

import org.apache.wicket.behavior.HeaderContributor;
import org.apache.wicket.markup.html.WebPage;
import org.mix3.blog.WicketApplication;

public class AbstractFrame extends WebPage{
	public AbstractFrame(){
		add(HeaderContributor.forCss(WicketApplication.class, "resources/VectorLover.css"));
	}
}
