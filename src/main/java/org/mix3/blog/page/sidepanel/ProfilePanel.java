package org.mix3.blog.page.sidepanel;

import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.Model;
import org.mix3.blog.frame.MyPanel;

@SuppressWarnings("serial")
public class ProfilePanel extends MyPanel{
	public ProfilePanel(String id) {
		super(id);
		add(new MultiLineLabel("profile", new Model(service.getSetting().getAbout())).setEscapeModelStrings(false));
	}
}
