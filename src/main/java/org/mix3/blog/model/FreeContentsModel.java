package org.mix3.blog.model;

import java.io.Serializable;

import org.mix3.blog.entity.FreeContents;
import org.mix3.blog.entity.FreeSideContents;

@SuppressWarnings("serial")
public class FreeContentsModel implements Serializable{
	private int id;
	private int priority;
	private String title;
	private String contents;
	
	public FreeContentsModel(){
		this.id = 0;
	}
	public FreeContentsModel(FreeContents freeContents){
		this.id = freeContents.getID();
		this.priority = freeContents.getPriority();
		this.title = freeContents.getTitle();
		this.contents = freeContents.getContents();
	}
	public FreeContentsModel(FreeSideContents freeContents){
		this.id = freeContents.getID();
		this.priority = freeContents.getPriority();
		this.title = freeContents.getTitle();
		this.contents = freeContents.getContents();
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPriority() {
		return priority;
	}
	public void setPriority(int priority) {
		this.priority = priority;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
}
