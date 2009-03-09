package org.mix3.blog.auth.page;

import java.sql.SQLException;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.mix3.blog.model.FreeContentsModel;

public class FreeSideContentsEditPage extends AbstractFreeContentsEditPage{
	public FreeSideContentsEditPage(PageParameters parameters) {
		super(parameters);
	}

	@Override
	protected void createFreeContents(FreeContentsModel fcm)
			throws SQLException {
		service.createFreeSideContents(fcm);
	}

	@Override
	protected void deleteFreeContents(FreeContentsModel fcm)
			throws SQLException {
		service.deleteFreeSideContents(fcm);
	}

	@Override
	protected List<FreeContentsModel> getFreeContentsList() throws SQLException {
		return service.getFreeSideContentsList();
	}

	@Override
	protected void updateFreeContents(FreeContentsModel fcm)
			throws SQLException {
		service.updateFreeSideContents(fcm);
	}

	@Override
	protected void updateFreeContentsList(List<FreeContentsModel> fcmList)
			throws SQLException {
		service.updateFreeSideContentsList(fcmList);
	}

	@Override
	protected Class<? extends AbstractFreeContentsEditPage> getCls() {
		return FreeSideContentsEditPage.class;
	}

	@Override
	protected FreeContentsModel getFreeContents(int id) throws SQLException {
		return service.getFreeSideContents(id);
	}
}
