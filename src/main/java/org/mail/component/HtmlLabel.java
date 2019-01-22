package org.mail.component;

import com.vaadin.flow.component.html.Label;

public class HtmlLabel extends Label {
	
	private static final long serialVersionUID = 1L;
	private String Text;
	
	public HtmlLabel() {
		
	}

	public HtmlLabel(String text) {
		this.getElement().setProperty("innerHTML", text);
	}

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		this.getElement().setProperty("innerHTML", text);
	}
	


}
