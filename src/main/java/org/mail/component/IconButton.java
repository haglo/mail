package org.mail.component;

import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

public class IconButton extends Composite<FlexLayout> {

	private static final long serialVersionUID = 1L;

	private Button button;
	private Icon icon;

	public IconButton(String text, ICON_POSITION icp, VaadinIcon vaadinIcon) {
		int size = 50;
		int height = size + 4;

		button = new Button();
		button.setHeight("" + height + "px");

		icon = new Icon(vaadinIcon);
		icon.setSize("" + size / 2 + "px");

		if (icp == ICON_POSITION.TOP) {
			Div divText = new Div();
			divText.setText(text);
			button.getElement().appendChild(icon.getElement());
			button.getElement().appendChild(divText.getElement());
		}

		if (icp == ICON_POSITION.BOTTOM) {
			Div divText = new Div();
			divText.setText(text);
			button.getElement().appendChild(divText.getElement());
			button.getElement().appendChild(icon.getElement());
		}

		if (icp == ICON_POSITION.LEFT) {
			button.setText(text);
			button.setIcon(icon);
			button.setIconAfterText(false);
		}
		
		if (icp == ICON_POSITION.RIGHT) {
			button.setText(text);
			button.setIcon(icon);
			button.setIconAfterText(true);
		}

		getContent().add(button);
	}

	public enum ICON_POSITION {
		TOP, LEFT, BOTTOM, RIGHT;
	}

}
