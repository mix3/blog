package org.mix3.blog.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.mix3.blog.entity.Response;

@SuppressWarnings("serial")
public class ResponseModel implements Serializable{
	private Integer id;
	private Integer articleid;
	private String articletitle;
	private String name;
	private String contents;
	private Timestamp date;

	public ResponseModel(){}
	public ResponseModel(int id, String name, String contents){
		setArticleid(id);
		setName(name);
		setContents(contents);
	}
	public ResponseModel(Response r){
		setId(r.getID());
		setArticleid(r.getArticle().getID());
		setArticletitle(r.getArticle().getTitle());
		setName(r.getName());
		setContents(r.getContents());
		setDate(r.getDate());
	}
	public String getDateInfo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy'年'MM'月'dd'日' hh:mm");
		return sdf.format(date);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getArticleid() {
		return articleid;
	}
	public void setArticleid(Integer articleid) {
		this.articleid = articleid;
	}
	public String getArticletitle() {
		return articletitle;
	}
	public void setArticletitle(String articletitle) {
		this.articletitle = articletitle;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getDate() {
		return date;
	}
	public void setDate(Timestamp date) {
		this.date = date;
	}
}
