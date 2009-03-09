package org.mix3.blog.component;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.coding.AbstractRequestTargetUrlCodingStrategy;
import org.apache.wicket.util.string.Strings;
import org.mix3.blog.service.Module;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ThumbnailUrlCodingStrategy extends AbstractRequestTargetUrlCodingStrategy{
	private ThumbnailRequestTarget thumbnailRequestTarget;
	
	public ThumbnailUrlCodingStrategy(String path) {
		super(path);
		Injector injector = Guice.createInjector(new Module());
		thumbnailRequestTarget = injector.getInstance(ThumbnailRequestTarget.class);
	}

	public IRequestTarget decode(RequestParameters requestParameters) {
		String param = Strings.afterLast(requestParameters.getPath(), '/');
		thumbnailRequestTarget.setParam(param);
		return thumbnailRequestTarget;
	}

	public CharSequence encode(IRequestTarget requestTarget) {
		ThumbnailRequestTarget target = (ThumbnailRequestTarget) requestTarget;
		StringBuilder builder = new StringBuilder();
		builder.append(getMountPath());
		builder.append("/" + target.getParam());
		return builder.toString();
	}

	public boolean matches(IRequestTarget requestTarget) {
		return requestTarget instanceof ThumbnailRequestTarget;
	}

}
