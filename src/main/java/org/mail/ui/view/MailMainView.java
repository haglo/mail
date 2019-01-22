package org.mail.ui.view;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

import org.mail.component.DownloadLink;
import org.mail.model.entity.PmailFolder;
import org.mail.service.PmailService;
import org.mail.ui.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class MailMainView extends VerticalLayout implements Const {

	private static final long serialVersionUID = 1L;
	private MailListView mailListView;
	private MailDetailView mailDetailView;
	private SplitLayout mailContent;
	private VerticalLayout mailContentLeftPart;
	private SplitLayout mailContentRightPart;
	
	/**
	    - mailContent (SplitPanel)
			- mailContentLeftPart (VerticalLayout(
			- mailContentRightPart (SplitPanel)
				- mailListView
				- mailDetailView
	 */
	

	public MailMainView(@Autowired PmailService service) {
		setSizeFull();
		setMargin(false);

		mailContentLeftPart = new VerticalLayout();
		mailContentLeftPart.setMargin(false);
		mailContentLeftPart.setSizeFull();

		mailContentRightPart = new SplitLayout();
		mailContentRightPart.setOrientation(Orientation.HORIZONTAL);
		mailContentRightPart.setSplitterPosition(50);
		mailContentRightPart.setSizeFull();

		List<PmailFolder> pmailFolders = service.getFolders();

		for (PmailFolder pmf : pmailFolders) {
			Button btn = new Button(pmf.getPfolderName(), ev -> {
				mailListView = new MailListView(pmf, service, this);
				mailDetailView = new MailDetailView();
				mailContentRightPart.addToPrimary(mailListView);
				mailContentRightPart.addToSecondary(mailDetailView);

			});
			btn.addClassName("main-layout__left-nav");
			mailContentLeftPart.add(btn);
		}
		
		mailContent = new SplitLayout();
		mailContent.setOrientation(Orientation.HORIZONTAL);
		mailContent.addToPrimary(mailContentLeftPart);
		mailContent.addToSecondary(mailContentRightPart);
		mailContent.setSplitterPosition(40);
		mailContent.setSizeFull();

		add(mailContent);

	}

	public MailListView getMailListView() {
		return mailListView;
	}

	public void setMailListView(MailListView mailListView) {
		this.mailListView = mailListView;
	}

	public MailDetailView getMailDetailView() {
		return mailDetailView;
	}

	public void setMailDetailView(MailDetailView mailDetailView) {
		this.mailDetailView = mailDetailView;
	}

	public SplitLayout getMailContent() {
		return mailContent;
	}

	public void setMailContent(SplitLayout mailContent) {
		this.mailContent = mailContent;
	}

	public VerticalLayout getMailContentLeftPart() {
		return mailContentLeftPart;
	}

	public void setMailContentLeftPart(VerticalLayout mailContentLeftPart) {
		this.mailContentLeftPart = mailContentLeftPart;
	}

	public SplitLayout getMailContentRightPart() {
		return mailContentRightPart;
	}

	public void setMailContentRightPart(SplitLayout mailContentRightPart) {
		this.mailContentRightPart = mailContentRightPart;
	}

	public SplitLayout getEmailContent() {
		return mailContent;
	}

	public void setEmailContent(SplitLayout emailContent) {
		this.mailContent = emailContent;
	}

	public SplitLayout getEmailContentRightBar() {
		return mailContentRightPart;
	}
	

}

