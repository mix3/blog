package org.mix3.blog.auth.page;

import java.sql.SQLException;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.mix3.blog.model.FreeContentsModel;

public class FreeContentsEditPage extends AbstractFreeContentsEditPage{
	public FreeContentsEditPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void createFreeContents(FreeContentsModel fcm)
			throws SQLException {
		service.createFreeContents(fcm);
	}

	@Override
	protected void deleteFreeContents(FreeContentsModel fcm)
			throws SQLException {
		service.deleteFreeContents(fcm);
	}

	@Override
	protected List<FreeContentsModel> getFreeContentsList() throws SQLException {
		return service.getFreeContentsList();
	}

	@Override
	protected void updateFreeContents(FreeContentsModel fcm)
			throws SQLException {
		service.updateFreeContents(fcm);
	}

	@Override
	protected void updateFreeContentsList(List<FreeContentsModel> fcmList)
			throws SQLException {
		service.updateFreeContentsList(fcmList);
	}

	@Override
	protected Class<? extends AbstractFreeContentsEditPage> getCls() {
		return FreeContentsEditPage.class;
	}

	@Override
	protected FreeContentsModel getFreeContents(int id) throws SQLException {
		return service.getFreeContents(id);
	}
}
