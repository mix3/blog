package org.mix3.blog.page.panel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.mix3.blog.component.MyBookmarkablePageLink;
import org.mix3.blog.frame.MyPanel;
import org.mix3.blog.page.AbstractArticlePage;

@SuppressWarnings("serial")
public class NavigationPanel extends MyPanel{
	public NavigationPanel(String id,
			Class<? extends AbstractArticlePage> c, PageParameters parameters, int size, int limit) {
		super(id);
		int of = parameters.getInt("of", 0);
		PageParameters prevParameters = (PageParameters) parameters.clone();
		PageParameters nextParameters = (PageParameters) parameters.clone();
		prevParameters.put("of", String.valueOf(of+limit));
		nextParameters.put("of", String.valueOf(of-limit));
		BookmarkablePageLink bprev = new MyBookmarkablePageLink("prev", c, prevParameters, "前の"+limit+"件");
		BookmarkablePageLink bnext = new MyBookmarkablePageLink("next", c, nextParameters, "次の"+limit+"件");
		
		if(of <= 0){
			bnext.setVisible(false);
		}
		if(of+limit >= size){
			bprev.setVisible(false);
		}
		add(bprev);
		add(bnext);
	}
}
