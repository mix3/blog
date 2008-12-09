package org.mix3.blog.error;

import org.apache.wicket.Page;
import org.apache.wicket.Response;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;

public class ErrorRequestCycle extends WebRequestCycle{
	@Override
	public Page onRuntimeException(Page page, RuntimeException e) {
		return new ErrorPage(e);
	}

	public ErrorRequestCycle(WebApplication application, WebRequest request, Response response) {
		super(application, request, response);
	}
}
