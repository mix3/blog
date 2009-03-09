package org.mix3.blog;

import org.apache.wicket.application.ReloadingClassLoader;
import org.apache.wicket.protocol.http.ReloadingWicketFilter;

public class MyReloadingWicketFilter extends ReloadingWicketFilter {
	static {
		ReloadingClassLoader.includePattern("org.mix3.blog.*");
	}
}

