package org.mix3.blog.component.tabpage;

import java.io.Serializable;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.WebPage;

@SuppressWarnings("serial")
public class PageTab implements Serializable{
	private String title;
	private PageParameters parameters;
	private Class<? extends WebPage> cls;
	
	public PageTab(String title, Class<? extends WebPage> cls){
		this.title = title;
		this.parameters = null;
		this.cls = cls;
	}
	
	public PageTab(String title, Class<? extends WebPage> cls, PageParameters parameters){
		this.title = title;
		this.parameters = parameters;
		this.cls = cls;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public PageParameters getParameters() {
		return parameters;
	}
	public void setParameters(PageParameters parameters) {
		this.parameters = parameters;
	}
	public Class<? extends WebPage> getCls() {
		return cls;
	}
	public void setCls(Class<? extends WebPage> cls) {
		this.cls = cls;
	}
	
	public void onClick(){
	}
}
