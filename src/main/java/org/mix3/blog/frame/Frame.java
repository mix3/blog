package org.mix3.blog.frame;

import org.apache.wicket.markup.html.basic.Label;

public class Frame extends AbstractFrame{
	public Frame() throws Exception{
		add(new Label("sidebar", "sidebar"));
	}
}
