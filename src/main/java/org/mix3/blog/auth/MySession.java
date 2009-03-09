package org.mix3.blog.auth;

import org.apache.wicket.Request;
import org.apache.wicket.protocol.http.WebSession;

@SuppressWarnings("serial")
public class MySession extends WebSession{
	public MySession(Request request) {
		super(request);
	}
	
	private String userName;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public boolean isSignedIn() {
		return userName != null;
	}
}
