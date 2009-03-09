package org.mix3.blog.component;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.AppendingStringBuffer;

@SuppressWarnings("serial")
public class MyMultiLineLabel extends MultiLineLabel{
	@Override
	protected void onComponentTagBody(MarkupStream markupStream,
			ComponentTag openTag) {
		final CharSequence body = toMultilineMarkup(getModelObjectAsString());
		replaceComponentTagBody(markupStream, openTag, body);
	}
	public MyMultiLineLabel(String id, IModel model) {
		super(id, model);
	}
	public MyMultiLineLabel(String id, String string) {
		super(id, string);
	}
	
	private static CharSequence toMultilineMarkup(final CharSequence s){
		if (s == null){
			return null;
		}

		final AppendingStringBuffer buffer = new AppendingStringBuffer();
		int newlineCount = 0;
		
		boolean flag = false;
		buffer.append("<p>");
		for (int i = 0; i < s.length(); i++){
			final char c = s.charAt(i);
			
			if(c == '<'){
				StringBuffer str = new StringBuffer();
				try{
					for(int j = 0; j < 9; j++){
						str.append(s.charAt(i+j));
					}
				}catch(StringIndexOutOfBoundsException e){
				}
				if(str.toString().equals("<textarea")){
					flag = true;
				}
				if(str.toString().equals("</textare")){
					flag = false;
					newlineCount = 0;
				}
			}
			
			switch (c){
				case '\n' :
					newlineCount++;
					break;

				case '\r' :
					break;

				default :
					if(flag){
						for(int k = 0; k < newlineCount; k++){
							buffer.append('\n');
						}
					}else{
						for(int k = 0; k < newlineCount; k++){
							buffer.append("<br/>");
						}
					}
					buffer.append(c);
					newlineCount = 0;
					break;
			}
		}
		for(int k = 0; k < newlineCount; k++){
			buffer.append("<br/>");
		}
		buffer.append("</p>");
		return buffer;
	}
}
