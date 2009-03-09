package org.mix3.blog.auth.page;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.image.ContextImage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.link.PopupSettings;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.mix3.blog.auth.AuthenticatedWebPage;
import org.mix3.blog.model.ImageModel;
import org.mix3.blog.component.ImageRequestTarget;
import org.mix3.blog.component.LazyLoadingList;
import org.mix3.blog.component.ThumbnailRequestTarget;

public class UploadPage extends AuthenticatedWebPage{
	public UploadPage(){
		add(new UploadForm("uploadform"));
		add(new EditForm("listform"));
	}
	
	@SuppressWarnings("serial")
	public class UploadForm extends Form{
		@SuppressWarnings("unused")
		private MultiFileUploadField multiFileUploadField;
		private List<FileUpload> uploads = new ArrayList<FileUpload>();
		@SuppressWarnings("unused")
		private List<FileUpload> getUpload(){
			return uploads;
		}
		public UploadForm(String id) {
			super(id);
			add(new FeedbackPanel("feedback"));
			setMultiPart(true);
			add(multiFileUploadField = new MultiFileUploadField("fileInput", new PropertyModel(this, "uploads")));
			add(new Button("submit"){
				@Override
				public void onSubmit() {
					super.onSubmit();
					for(FileUpload u : uploads){
						try {
							if(u.getSize() > (1024 * 1024 * 2)){
								error(u.getClientFileName()+" Upload Must be less than 2M byte.");
							}else{
								service.createImage(new ImageModel(u.getClientFileName(), u.getInputStream()));
								setResponsePage(UploadPage.class);
							}
						} catch (SQLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			});
		}
	}
	
	@SuppressWarnings("serial")
	public class EditForm extends Form{
		public EditForm(String id) {
			super(id);
			final CheckGroup checkgroup = new CheckGroup("checkgroup", new ArrayList<ImageModel>());
			add(checkgroup);
			checkgroup.add(new CheckGroupSelector("checkall"));
			final List<ImageModel> imageModelList = new LazyLoadingImageList();
			PageableListView listView = new PageableListView("list", imageModelList, 10){
				@Override
				protected void populateItem(ListItem item) {
					final ImageModel a = (ImageModel)item.getModelObject();
					item.add(new Check("check", item.getModel()));
					ImageRequestTarget imageTarget = new ImageRequestTarget(Integer.toString(a.getId()));
					String imageUrl = urlFor(imageTarget).toString();
					ExternalLink link = new ExternalLink("img", new Model(imageUrl));
					item.add(link);
					if(a.getThumbnail() != null){
						ThumbnailRequestTarget thumbnailTarget = new ThumbnailRequestTarget(Integer.toString(a.getId()));
						String thumbnailUrl = urlFor(thumbnailTarget).toString();
						link.add(new ContextImage("img", new Model(thumbnailUrl)));
					}else{
						link.add(new ContextImage("img", new Model(imageUrl)));
					}
					link.add(new Label("title", a.getName()));
					item.add(new Label("date", a.getDate().toString()));
					item.add(new TextField("category", new PropertyModel(a, "strcategory")));
					item.add(new BookmarkablePageLink("popup", PopupPage.class, new PageParameters("id="+a.getId()+",thumbnail="+(a.getThumbnail()!=null?true:false)))
					.setPopupSettings(new PopupSettings(PageMap.forName("popuppagemap"))));
					item.add(new Button("delete"){
						@Override
						public void onSubmit() {
							try {
								service.deleteImage(a);
							} catch (SQLException e) {
								e.printStackTrace();
							}
							setResponsePage(UploadPage.class);
						}
					});
				}
			};
			checkgroup.add(new PagingNavigator("navi", listView));
			checkgroup.add(listView);
			checkgroup.add(new Button("deleteall"){
				@SuppressWarnings("unchecked")
				@Override
				public void onSubmit() {
					try {
						service.deleteImageList((ArrayList<ImageModel>)checkgroup.getModelObject());
					} catch (SQLException e) {
						e.printStackTrace();
					}
					setResponsePage(UploadPage.class);
				}
			});
			checkgroup.add(new Button("editall"){
				@Override
				public void onSubmit() {
					try {
						System.out.println("editall");
						service.updateImageList(imageModelList);
					} catch (SQLException e) {
						error("SQLException Error");
						e.printStackTrace();
					}
					setResponsePage(UploadPage.class);
				}
			});
		}
	}
	
	@SuppressWarnings("serial")
	public class LazyLoadingImageList extends LazyLoadingList<ImageModel>{
		@Override
		protected List<ImageModel> getPage(int start, int size)
				throws SQLException, IOException {
			return service.getImageList(size, start);
		}

		@Override
		public int size() {
			try {
				return service.getCountImageList();
			} catch (SQLException e) {
				error("SQLException Error");
				e.printStackTrace();
				return 0;
			}
		}
		
	}
}
