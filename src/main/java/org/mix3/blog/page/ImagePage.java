package org.mix3.blog.page;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.StringValueConversionException;
import org.mix3.blog.model.ImageModel;
import org.mix3.blog.component.ImageRequestTarget;
import org.mix3.blog.component.MyBookmarkablePageLink;
import org.mix3.blog.component.ThumbnailRequestTarget;
import org.mix3.blog.frame.Frame;

public class ImagePage extends Frame{
	@SuppressWarnings("serial")
	public ImagePage(PageParameters parameters){
		List<ImageModel> imageModelList = new ArrayList<ImageModel>();
		try {
			imageModelList = service.getCategoryImageList(
					parameters.getString("category"),
					service.getSetting().getListnum(),
					parameters.getInt("of", 0));
		} catch (StringValueConversionException e) {
			error("StringValueConversionException Error");
			e.printStackTrace();
		} catch (SQLException e) {
			error("SQLException Error");
			e.printStackTrace();
		}
		
		int of = parameters.getInt("of", 0);
		int limit = service.getSetting().getListnum();
		int size = imageModelList.size();
		PageParameters prevParameters = (PageParameters) parameters.clone();
		PageParameters nextParameters = (PageParameters) parameters.clone();
		prevParameters.put("of", String.valueOf(of+limit));
		nextParameters.put("of", String.valueOf(of-limit));
		BookmarkablePageLink bprev = new MyBookmarkablePageLink("prev", this.getClass(), prevParameters, "前の"+limit+"件");
		BookmarkablePageLink bnext = new MyBookmarkablePageLink("next", this.getClass(), nextParameters, "次の"+limit+"件");
		
		if(of <= 0){
			bnext.setVisible(false);
		}
		if(of+limit >= size){
			bprev.setVisible(false);
		}
		add(bprev);
		add(bnext);
		
		ListView listView = new ListView("imageList", imageModelList){
			@Override
			protected void populateItem(ListItem item) {
				ImageModel imageModel = (ImageModel)item.getModelObject();
				ImageRequestTarget imageTarget = new ImageRequestTarget(Integer.toString(imageModel.getId()));
				String imageUrl = urlFor(imageTarget).toString();
				ExternalLink link = new ExternalLink("img", new Model(imageUrl));
				item.add(link);
				if(imageModel.getThumbnail() != null){
					ThumbnailRequestTarget thumbnailTarget = new ThumbnailRequestTarget(Integer.toString(imageModel.getId()));
					String thumbnailUrl = urlFor(thumbnailTarget).toString();
					link.add(new ContextImage("img", new Model(thumbnailUrl)));
				}else{
					link.add(new ContextImage("img", new Model(imageUrl)));
				}
			}
		};
		add(listView);
		for(ImageModel im : imageModelList){
			System.out.println(im.getName());
		}
	}
}
