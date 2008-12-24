package org.mix3.blog.error;

import org.mix3.blog.frame.AbstractFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorPage extends AbstractFrame{
	private static final Logger logger = LoggerFactory.getLogger(ErrorPage.class);

	public ErrorPage(RuntimeException e){
		logger.warn(e.getMessage(), e);
		System.err.println(e.getCause().getCause().getMessage());
	}
} 