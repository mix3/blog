package org.mix3.blog.entity;

import java.io.InputStream;
import java.sql.Timestamp;
import java.sql.Types;

import net.java.ao.Entity;
import net.java.ao.ManyToMany;
import net.java.ao.schema.SQLType;

public interface Image extends Entity{
	public String getName();
	public void setName(String name);
	
	@SQLType(Types.BLOB)
	public byte[] getImage();
	public void setImage(InputStream image);
	
	@SQLType(Types.BLOB)
	public byte[] getThumbnail();
	public void setThumbnail(InputStream Thumbnail);
	
	@SQLType(Types.TIMESTAMP)
	public Timestamp getDate();
	public void setDate(Timestamp date);
	
	@ManyToMany(ImageToCategory.class)
	public Category[] getCategories();
}
