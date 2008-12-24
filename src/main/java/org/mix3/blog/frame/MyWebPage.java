package org.mix3.blog.frame;

import org.apache.wicket.markup.html.WebPage;
import org.mix3.blog.service.Service;

import com.google.inject.Inject;

public class MyWebPage extends WebPage{
	@Inject
	protected Service service;
}
