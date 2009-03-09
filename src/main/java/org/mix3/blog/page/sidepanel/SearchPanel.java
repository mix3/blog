package org.mix3.blog.page.sidepanel;

import org.apache.wicket.PageParameters;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ImageButton;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.mix3.blog.WicketApplication;
import org.mix3.blog.frame.MyPanel;
import org.mix3.blog.page.SearchPage;

@SuppressWarnings("serial")
public class SearchPanel extends MyPanel{
	public SearchPanel(String id) {
		super(id);
		add(new MyForm("form"));
	}
	
	public class MyForm extends Form{
		private String word;
		
		public String getWord() {
			return word;
		}
		public void setWord(String word) {
			this.word = word;
		}

		public MyForm(String id) {
			super(id);
			add(new TextField("word", new PropertyModel(this, "word")));
			add(new ImageButton("submit"){
				@Override
				public void onSubmit() {
					super.onSubmit();
					setResponsePage(SearchPage.class, new PageParameters("word="+getWord()));
				}

				@Override
				protected ResourceReference getImageResourceReference() {
					return new ResourceReference(WicketApplication.class, "resources/search.gif");
				}
			});
		}
	}
}
