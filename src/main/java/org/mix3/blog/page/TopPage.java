package org.mix3.blog.page;

import javax.management.RuntimeErrorException;

import org.apache.wicket.markup.html.basic.Label;
import org.mix3.blog.frame.Frame;

public class TopPage extends Frame{
	public TopPage() throws Exception{
		if(true){
			throw new RuntimeErrorException(new Error("error"), "error_message");
		}
		add(new Label("message", "toppage"));
	}
}
