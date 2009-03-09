package org.mix3.blog.page;

import java.security.NoSuchAlgorithmException;

import org.apache.wicket.Session;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.mix3.blog.auth.MySession;
import org.mix3.blog.auth.page.Manage;
import org.mix3.blog.model.SettingModel;
import org.mix3.blog.frame.Frame;
import org.mix3.blog.util.Utils;

public class SignInPage extends Frame{
	private SettingModel settingModel;
	
	public SignInPage(){
		settingModel = service.getSetting();
		add(new LoginForm("form"));
	}
	@SuppressWarnings("serial")
	public class LoginForm extends Form{
		private TextField textField = new TextField("id", new Model(""));
		private PasswordTextField passwordTextField = new PasswordTextField("pass", new Model(""));

		public LoginForm(String id) {
			super(id);
			add(textField);
			add(passwordTextField.setRequired(false));
			add(new Button("submit"){
				@Override
				public void onSubmit() {
					String uid = textField.getModelObjectAsString();
					String pass = null;
					try {
						pass = Utils.digest(passwordTextField.getModelObjectAsString());
					} catch (NoSuchAlgorithmException e) {
						error("NoSuchAlgorithmException Error");
						e.printStackTrace();
					}
					if(settingModel.getUid().equals(uid) && settingModel.getPass().equals(pass)){
						((MySession)Session.get()).setUserName(uid);
						if(!continueToOriginalDestination()){
							setResponsePage(Manage.class);
						}
					}else{
						error("認証に失敗しました");
					}
				}
			});
		}
	}
}
