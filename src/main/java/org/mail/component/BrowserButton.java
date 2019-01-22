package org.mail.component;

import com.vaadin.flow.component.dependency.HtmlImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.page.Viewport;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

public class BrowserButton extends Label {

	private static final long serialVersionUID = 1L;
	
	private String text;
	private LINK_TYPE linkType;
	

	public BrowserButton() {
		this.text="Click";
		this.linkType=LINK_TYPE.NEW_TAB;
		addClassName("linkButton");
		
		
		
		

//		Anchor a = new Anchor();
//		a.setText("My link text");
//		Icon ic = new Icon(VaadinIcon.SIGN_OUT);
//		a.add(ic);
//		a.getElement().addEventListener("click", e -> {
//			VaadinSession.getCurrent().getSession().invalidate();
//			UI.getCurrent().getPage().reload();
//		});
		
		
//		label.getElement().setProperty("innerHTML", "<button>My html button</button>");
	}


	public Label createLink() {
		String tmp = "";
		if (linkType == LINK_TYPE.NEW_TAB) {
//			tmp= "<a href=\"http://deinlinkziel.de\"><button>Klick!</button></a>";
			tmp= "<a href=\"/\" target=\"_blank\"><button class=\"linkButton\"><p>open in another tab<p><img src=\"https://www.tutorialspoint.com/latest/inter-process-communication.png \" /></button></a>";
			
			
		}
		
		if (linkType == LINK_TYPE.NEW_BROWSER) {
			tmp= "<a href=\"http://www.google.de\"><button>Klick!</button></a>";
						
		}
		Label label = new Label();
//		label.getElement().setProperty("innerHTML", "<button>My html button</button>");
		label.getElement().setProperty("innerHTML", tmp);
		
		return label;
	}
	
	
	
	public String getText() {
		return text;
	}


	public void setText(String linkText) {
		this.text = linkText;
	}


	public LINK_TYPE getLinkType() {
		return linkType;
	}

	public void setLinkType(LINK_TYPE linkType) {
		this.linkType = linkType;
	}


	public enum LINK_TYPE {
		NEW_TAB, NEW_BROWSER;
	}
}
