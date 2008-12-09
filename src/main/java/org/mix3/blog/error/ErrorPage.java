package org.mix3.blog.error;

import org.mix3.blog.frame.AbstractFrame;
import org.mix3.blog.page.TopPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ErrorPage extends AbstractFrame{
	private static final Logger logger = LoggerFactory.getLogger(TopPage.class);

	public ErrorPage(RuntimeException e){
		logger.warn(e.getCause().getCause().getMessage(), e);
//		System.out.println(e.getCause().getCause().getMessage());
	}
} 