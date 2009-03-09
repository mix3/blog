package org.mix3.blog.frame;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.mix3.blog.service.Service;

import com.google.inject.Inject;

@SuppressWarnings("serial")
public class MyPanel extends Panel{
	@Inject
	protected Service service;
	
	public MyPanel(String id) {
		super(id);
	}
	public MyPanel(String id, IModel model) {
		super(id, model);
	}
}
