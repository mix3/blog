package org.mix3.blog.page.sidepanel;

import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.mix3.blog.frame.MyPanel;

@SuppressWarnings("serial")
public class FeedPanel extends MyPanel{
	public FeedPanel(final String id) {
		super(id);
		add(new ExternalLink("feed", urlFor(new ResourceReference("myFeed")).toString()));
	}
}
