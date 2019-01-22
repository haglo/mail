package org.mail.ui;

import org.mail.component.BrowserButton;
import org.mail.component.BrowserButton.LINK_TYPE;
import org.mail.component.IconButton;
import org.mail.component.IconButton.ICON_POSITION;
import org.mail.ui.imap.Imap;
import org.mail.ui.view.MailWriteView;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class TopMenu extends Div {

	private static final long serialVersionUID = 1L;

	private Button callButton;

	public TopMenu(@Autowired Imap imap) {
		addClassName("main-layout__top-nav");

		callButton = new Button("Call", e -> {
			imap.readFromImap();
//			callButton.getUI().ifPresent(ui -> ui.navigate("MailMainView"));
		});
		callButton.setIcon(VaadinIcon.CLOUD_DOWNLOAD.create());
		callButton.addClassName("main-layout__top-nav-item");

		Button writeButton = new Button("Write", e -> {
			MailWriteView detailView = new MailWriteView();
			detailView.open();
		});
		writeButton.setIcon(VaadinIcon.EDIT.create());

		Button answerButton = new Button("Answer", e -> {
			MailWriteView detailView = new MailWriteView();
			detailView.open();
		});
		answerButton.setIcon(VaadinIcon.ARROW_BACKWARD.create());

		Button forwardButton = new Button("Forward", e -> {
			MailWriteView detailView = new MailWriteView();
			detailView.open();
		});
		forwardButton.setIcon(VaadinIcon.ARROW_FORWARD.create());

		Button printButton = new Button("Print", e -> {
			MailWriteView detailView = new MailWriteView();
			detailView.open();
		});
		printButton.setIcon(VaadinIcon.PRINT.create());

		Button deleteButton = new Button("Delete", e -> {
			MailWriteView detailView = new MailWriteView();
			detailView.open();
		});
		deleteButton.setIcon(VaadinIcon.TRASH.create());

		HorizontalLayout emailNavBar = new HorizontalLayout(callButton, writeButton, answerButton, forwardButton, printButton, deleteButton);
		add(emailNavBar);
	}

}
