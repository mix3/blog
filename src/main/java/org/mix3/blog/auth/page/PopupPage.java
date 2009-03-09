package org.mix3.blog.auth.page;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.mix3.blog.auth.AuthenticatedWebPage;
import org.mix3.blog.model.SettingModel;

public class PopupPage extends AuthenticatedWebPage{
	private SettingModel settingModel;
	
	public PopupPage(PageParameters parameters){
		settingModel = service.getSetting();
		int id = parameters.getInt("id");
		boolean thumbnail = parameters.getBoolean("thumbnail");
		String text = "<a href=\""+settingModel.getBlogurl()+"img/"+id+"\">";
		if(thumbnail){
			text = text+"<img src=\""+settingModel.getBlogurl()+"img/thumbnail/"+id+"\" />";
		}else{
			text = text+"<img src=\""+settingModel.getBlogurl()+"img/"+id+"\" />";
		}
		text = text+"</a>";
		add(new MyForm("form", new Model(text)));
	}
	
	@SuppressWarnings("serial")
	private class MyForm extends Form{
		public MyForm(String id, IModel model) {
			super(id, model);
			add(new TextArea("text", model));
		}
	}
}
