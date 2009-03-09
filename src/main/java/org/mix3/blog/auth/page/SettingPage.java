package org.mix3.blog.auth.page;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.mix3.blog.auth.AuthenticatedWebPage;
import org.mix3.blog.model.SettingModel;
import org.mix3.blog.util.Utils;

public class SettingPage extends AuthenticatedWebPage{
	private SettingModel settingModel;
	
	public SettingPage(){
		settingModel = service.getSetting();
		add(new SettingForm("form", new CompoundPropertyModel(settingModel)));
	}
	
	@SuppressWarnings("serial")
	public class SettingForm extends Form{
		public SettingForm(String id, IModel model) {
			super(id, model);
			add(new TextField("blogname"));
			add(new TextField("blogurl"));
			add(new TextField("description"));
			add(new TextField("uid"));
			add(new PasswordTextField("pass"));
			add(new TextArea("about"));
			
			add(new Button("submit"){
				@Override
				public void onSubmit() {
					try {
						settingModel.setPass(Utils.digest(settingModel.getPass()));
						service.updateSetting(settingModel);
					} catch (NoSuchAlgorithmException e) {
						error("NoSuchAlgorithmException Error");
						e.printStackTrace();
					} catch (SQLException e) {
						error("SQLException Error");
						e.printStackTrace();
					}
					setResponsePage(Manage.class);
				}
			});
		}
	}
}
