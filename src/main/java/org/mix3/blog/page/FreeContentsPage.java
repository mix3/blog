package org.mix3.blog.page;

import java.sql.SQLException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.util.string.StringValueConversionException;
import org.mix3.blog.model.FreeContentsModel;
import org.mix3.blog.frame.Frame;

public class FreeContentsPage extends Frame{
	public FreeContentsPage(PageParameters parameters){
		try {
			FreeContentsModel fcm = service.getFreeContents(parameters.getInt("id"));
			add(new Label("fc_title", fcm.getTitle()));
			add(new MultiLineLabel("fc_contents", fcm.getContents()).setEscapeModelStrings(false));
		} catch (StringValueConversionException e) {
			error("StringValueConversionException Error");
			e.printStackTrace();
		} catch (SQLException e) {
			error("SQLException Error");
			e.printStackTrace();
		}
	}
}
