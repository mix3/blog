package org.mix3.blog.service;

import java.io.IOException;
import java.sql.SQLException;

import org.mix3.blog.model.ImageModel;
import org.mix3.blog.model.SettingModel;

import com.google.inject.ImplementedBy;

@ImplementedBy(ServiceImpl.class)
public interface Service {
	public SettingModel getSetting();
	public ImageModel getImage(int id) throws SQLException, IOException;
}
