package org.mix3.blog.component;

import java.io.IOException;
import java.sql.SQLException;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.mix3.blog.service.Service;

import com.google.inject.Inject;

public class ThumbnailRequestTarget implements IRequestTarget{
	@Inject
	private Service service;
	private String param;

	public ThumbnailRequestTarget(){
		
	}
	public ThumbnailRequestTarget(String param) {
		this.param = param;
	}

	public void detach(RequestCycle requestCycle) {
	}
	@SuppressWarnings("serial")
	public void respond(RequestCycle requestCycle) {
		DynamicImageResource imageResource = new DynamicImageResource(){
			@Override
			protected byte[] getImageData() {
				try {
					return service.getImage(Integer.parseInt(param)).getThumbnail();
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				} catch (IOException e) {
					e.printStackTrace();
					return null;
				}
			}
		};
		imageResource.onResourceRequested();
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param){
		this.param = param;
	}
}
