package org.mix3.blog.auth;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.mix3.blog.page.SignInPage;

public class MyAuthorizationStrategy implements IAuthorizationStrategy{
	public boolean isActionAuthorized(Component component, Action action) {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean isInstantiationAuthorized(Class componentClass) {
		if(AuthenticatedWebPage.class.isAssignableFrom(componentClass)){
			if(((MySession)Session.get()).isSignedIn()){
				return true;
			}
			throw new RestartResponseAtInterceptPageException(SignInPage.class);
		}
		return true;
	}
}
