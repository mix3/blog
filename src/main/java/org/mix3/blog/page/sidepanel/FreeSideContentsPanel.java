package org.mix3.blog.page.sidepanel;

import java.sql.SQLException;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.mix3.blog.model.FreeContentsModel;
import org.mix3.blog.frame.MyPanel;

@SuppressWarnings("serial")
public class FreeSideContentsPanel extends MyPanel{
	public FreeSideContentsPanel(String id) {
		super(id);
		try {
			ListView listView = new ListView("list", service.getFreeSideContentsList()){
				@Override
				protected void populateItem(ListItem item) {
					FreeContentsModel fcm = (FreeContentsModel)item.getModelObject();
					item.add(new Label("title", new Model(fcm.getTitle())));
					item.add(new MultiLineLabel("contents", new Model(fcm.getContents())).setEscapeModelStrings(false));
				}
			};
			add(listView);
		} catch (SQLException e) {
			error("SQLException Error");
			e.printStackTrace();
		}

	}
}
