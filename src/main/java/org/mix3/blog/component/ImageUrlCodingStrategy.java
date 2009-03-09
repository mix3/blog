package org.mix3.blog.component;

import org.apache.wicket.IRequestTarget;
import org.apache.wicket.request.RequestParameters;
import org.apache.wicket.request.target.coding.AbstractRequestTargetUrlCodingStrategy;
import org.apache.wicket.util.string.Strings;
import org.mix3.blog.service.Module;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class ImageUrlCodingStrategy extends AbstractRequestTargetUrlCodingStrategy{
	private ImageRequestTarget imageRequestTarget;
	
	public ImageUrlCodingStrategy(String path) {
		super(path);
		Injector injector = Guice.createInjector(new Module());
		imageRequestTarget = injector.getInstance(ImageRequestTarget.class);
	}

	public IRequestTarget decode(RequestParameters requestParameters) {
		String param = Strings.afterLast(requestParameters.getPath(), '/');
		imageRequestTarget.setParam(param);
		return imageRequestTarget;
	}

	public CharSequence encode(IRequestTarget requestTarget) {
		ImageRequestTarget target = (ImageRequestTarget) requestTarget;
		StringBuilder builder = new StringBuilder();
		builder.append(getMountPath());
		builder.append("/" + target.getParam());
		return builder.toString();
	}

	public boolean matches(IRequestTarget requestTarget) {
		return requestTarget instanceof ImageRequestTarget;
	}

}
