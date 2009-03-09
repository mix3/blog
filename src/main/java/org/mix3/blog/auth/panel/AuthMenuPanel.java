package org.mix3.blog.auth.panel;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.mix3.blog.auth.MySession;
import org.mix3.blog.auth.page.CreatePage;
import org.mix3.blog.auth.page.EditPage;
import org.mix3.blog.auth.page.FreeContentsEditPage;
import org.mix3.blog.auth.page.FreeSideContentsEditPage;
import org.mix3.blog.auth.page.SettingPage;
import org.mix3.blog.auth.page.UploadPage;
import org.mix3.blog.page.TopPage;

@SuppressWarnings("serial")
public class AuthMenuPanel extends Panel{
	public AuthMenuPanel(String id) {
		super(id);
//		List<PageTab> list = new ArrayList<PageTab>();
//		list.add(new PageTab("Setting", SettingPage.class, new PageParameters()));
//		list.add(new PageTab("Create", CreatePage.class, new PageParameters()));
//		list.add(new PageTab("Edit", EditPage.class, new PageParameters()));
//		list.add(new PageTab("Edit", EditPage.class){
//			public void onClick() {
//				setResponsePage(EditPage.class);
//			}
//		});
//		list.add(new PageTab("Upload", UploadPage.class, new PageParameters()));
//		list.add(new PageTab("Logout", TopPage.class){
//			@Override
//			public void onClick() {
//				((MySession)getSession()).setUserName(null);
//				setResponsePage(TopPage.class);
//			}
//		});
//		add(new TabbedPagePanel("menu", list));
		
		add(new Link("setting"){
			@Override
			public void onClick() {
				setResponsePage(SettingPage.class);
			}
		});
		add(new Link("create"){
			@Override
			public void onClick() {
				setResponsePage(CreatePage.class);
			}
		});
		add(new Link("edit"){
			@Override
			public void onClick() {
				setResponsePage(EditPage.class);
			}
		});
		add(new Link("freeContents"){
			@Override
			public void onClick() {
				setResponsePage(FreeContentsEditPage.class);
			}
		});
		add(new Link("sideContents"){
			@Override
			public void onClick() {
				setResponsePage(FreeSideContentsEditPage.class);
			}
		});
		add(new Link("upload"){
			@Override
			public void onClick() {
				setResponsePage(UploadPage.class);
			}
		});
		add(new Link("logout"){
			@Override
			public void onClick() {
				((MySession)getSession()).setUserName(null);
				setResponsePage(TopPage.class);
			}
		});
	}
}
