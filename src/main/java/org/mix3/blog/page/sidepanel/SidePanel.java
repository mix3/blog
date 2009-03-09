package org.mix3.blog.page.sidepanel;

import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import org.mix3.blog.frame.MyPanel;

@SuppressWarnings("serial")
public class SidePanel extends MyPanel{
	public SidePanel(String id) throws SQLException{
		super(id);
		add(new ProfilePanel("profilePanel"));
		if(false){
			throw new RuntimeErrorException(new Error("error"), "error_message");
		}
		add(new CategoryPanel("categoryPanel"));
		add(new MonthlyPanel("monthlyPanel"));
		add(new SearchPanel("searchPanel"));
		add(new ImageCategoryPanel("imageCategoryPanel"));
		add(new FreeSideContentsPanel("freeSideContentsPanel"));
	}
}
