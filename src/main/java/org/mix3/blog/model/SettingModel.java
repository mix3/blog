package org.mix3.blog.model;

import java.io.Serializable;

import org.mix3.blog.entity.Setting;

@SuppressWarnings("serial")
public class SettingModel implements Serializable{
	private String uid;
	private String pass;
	private String blogname;
	private String blogurl;
	private String description;
	private String about;
	private int listnum;
	
	public SettingModel(){}
	public SettingModel(Setting setting){
		this.uid = setting.getUid();
		this.pass = setting.getPass();
		this.blogname = setting.getBlogname();
		this.blogurl = setting.getBlogurl();
		this.description = setting.getDescription();
		this.about = setting.getAbout();
		this.listnum = setting.getListnum();
	}
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getBlogname() {
		return blogname;
	}
	public void setBlogname(String blogname) {
		this.blogname = blogname;
	}
	public String getBlogurl() {
		return blogurl;
	}
	public void setBlogurl(String blogurl) {
		this.blogurl = blogurl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAbout() {
		return about;
	}
	public void setAbout(String about) {
		this.about = about;
	}
	public int getListnum() {
		return listnum;
	}
	public void setListnum(int listnum) {
		this.listnum = listnum;
	}
}
