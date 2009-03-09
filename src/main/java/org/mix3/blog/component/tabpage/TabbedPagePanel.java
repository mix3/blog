package org.mix3.blog.component.tabpage;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.Loop.LoopItem;
import org.apache.wicket.markup.html.panel.Panel;

@SuppressWarnings("serial")
public class TabbedPagePanel extends Panel{
	private List<PageTab> tabs;
	
	public TabbedPagePanel(String id, List<PageTab> tabs){
		super(id);

		if (tabs == null){
			throw new IllegalArgumentException("argument [tabs] cannot be null");
		}
		
		this.tabs = tabs;
		
		WebMarkupContainer tabsContainer = new WebMarkupContainer("tabs-container"){
			protected void onComponentTag(ComponentTag tag){
				super.onComponentTag(tag);
				tag.put("class", getTabContainerCssClass());
			}
		};
		add(tabsContainer);
		
		tabsContainer.add(new Loop("tabs", this.tabs.size()){
			@Override
			protected LoopItem newItem(int iteration) {
				return newTabContainer(iteration);
			}

			protected void populateItem(LoopItem item){
				int index = item.getIteration();
				PageTab pagePanel = TabbedPagePanel.this.tabs.get(index);
				WebMarkupContainer titleLink = newLink("link", pagePanel);

				titleLink.add(newTitle("title", pagePanel.getTitle()));
				item.add(titleLink);
			}
		});
	}
	
	protected LoopItem newTabContainer(int tabIndex){
		return new LoopItem(tabIndex){
			protected void onComponentTag(ComponentTag tag){
				super.onComponentTag(tag);
				
				String cssClass = (String)tag.getString("class");
				if (cssClass == null){
					cssClass = " ";
				}
				cssClass += " tab" + getIteration();

				PageTab pagePanel = TabbedPagePanel.this.tabs.get(getIteration());

//				System.out.println(getRequestCycle().getResponsePageClass().equals(pagePanel.getCls()));
//				System.out.println(getRequestCycle().getPageParameters());
//				System.out.println(pagePanel.getParameters());
//				System.out.println("-----");
				if(getRequestCycle().getResponsePageClass() != null
						&& pagePanel.getCls() != null
						&& getRequestCycle().getPageParameters() != null
						&& pagePanel.getParameters() != null){
					if(getRequestCycle().getResponsePageClass().equals(pagePanel.getCls())
							&& getRequestCycle().getPageParameters().equals(pagePanel.getParameters())){
						cssClass += " selected";
					}
				}
				if (getIteration() == getTabs().size() - 1){
					cssClass += " last";
				}
				tag.put("class", cssClass.trim());
			}
		};
	}
	
	protected String getTabContainerCssClass(){
		return "tab-row";
	}
	
	public final List<PageTab> getTabs(){
		return tabs;
	}
	
	protected Component newTitle(String titleId, String titleModel){
		return new Label(titleId, titleModel);
	}
	
	protected WebMarkupContainer newLink(String linkId, final PageTab pagePanel){
		if(pagePanel.getParameters() == null){
			return new Link(linkId){
				@Override
				public void onClick() {
					pagePanel.onClick();
				}
			};
		}else{
			return new BookmarkablePageLink(linkId, pagePanel.getCls(), pagePanel.getParameters());
		}
	}
}
