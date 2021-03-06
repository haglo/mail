package org.mail.ui.view;


import org.mail.component.ExtendedDialog;
import org.mail.ui.smtp.*;
import org.mail.ui.utils.Const;
import org.mail.common.MailServer;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

/**
 * Window to write emails
 * 
 * @author haglo
 *
 */

public class MailWriteView extends ExtendedDialog implements Const {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private VerticalLayout mainContent;
	private MailOut mailOut;

	public MailWriteView() {
		super("Write Email");
		setWidth("600px");

		MailServer mailServer = new MailServer();
		Smtp smtp = new Smtp();
		mailOut = new MailOut(smtp.getSession());

		mainContent = new VerticalLayout();
		mainContent.setSizeFull();

		TextField txfTo = new TextField();
		txfTo.setValue("h.g.gloeckler@gmx.de");
		TextField txfCC = new TextField();
		txfCC.setValue("h.g.gloeckler@gmail.com");
		TextField txfBC = new TextField();
		txfBC.setValue("hans-georg.gloeckler@uni-ulm.de, hans-georg.gloeckler@gimtex.de");
		TextField txfSubject = new TextField();
		txfSubject.setValue("Test-Email von Pilgerapp");
		TextArea rta = new TextArea();
		rta.setValue("<b>Hallo Welt with HTML bold</b>" + Signature.getSignaturHans());
		rta.setSizeFull();
		
		ExtendedUpload extendedUpload = new ExtendedUpload();
		Div uploadArea = new Div();
		uploadArea.add(extendedUpload.getUpload());

		Button sendButton = new Button("Send", ev -> {
			mailOut.setFrom(mailServer.getSmtpUsername());
			mailOut.setReplyTo(mailServer.getSmtpReplyTo());
			mailOut.setTo(txfTo.getValue());
			mailOut.setCc(txfCC.getValue());
			mailOut.setBcc(txfBC.getValue());
			mailOut.setSubject(txfSubject.getValue());
			mailOut.setHtmlContent(rta.getValue());
			mailOut.setAiFiles(extendedUpload.getAiFiles());

			try {
				smtp.send(mailOut.getMessage());
				Notification.show("Send success");
			} catch (Exception e) {
				e.printStackTrace();
				Notification.show("Send Error");
			} finally {
				close();
			}

		});
		sendButton.setClassName("alignright");

		mainContent.add(txfTo);
		mainContent.add(txfCC);
		mainContent.add(txfBC);
		mainContent.add(txfSubject);
		mainContent.add(rta);
		HorizontalLayout bottomMenuBar = new HorizontalLayout(uploadArea, sendButton);
		mainContent.add(bottomMenuBar);
		add(mainContent);
//		addWithTextOnTop(mainContent);

	}

}