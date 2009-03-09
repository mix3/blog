package org.mix3.blog.auth.page;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.mix3.blog.auth.AuthenticatedWebPage;
import org.mix3.blog.model.FreeContentsModel;

public class PreviewFreeContentsPage  extends AuthenticatedWebPage{
	public PreviewFreeContentsPage(final AbstractFreeContentsEditPage afce, final ModalWindow window, final FreeContentsModel fcm){
		add(new Label("pre_title", fcm.getTitle()));
		add(new MultiLineLabel("pre_contents", fcm.getContents()).setEscapeModelStrings(false));
	}
}
