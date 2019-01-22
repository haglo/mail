package org.mail.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.mail.component.HtmlLabel;

import com.google.common.base.Strings;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class MailDetailView extends VerticalLayout {

	private static final long serialVersionUID = 1L;
	private Label lblFrom;
	private Label lblSubject;
	private Label lblReplyTo;
	private Label lblTO;
	private Label lblCC;
	private Label lblBCC;
	private Label lblSendDate;
	private Label lblAttachmentNumber;
	private Label lblAttachmentFileNames;
	private Label lblAttachmentFilePath;
	private Label lblAttachmentFullFileName;
	private List<Button> attachments;
	private HorizontalLayout attachmentPanel;
	private HtmlLabel htmlArea;
	private String rawMail;
	private HorizontalLayout footer;

	public MailDetailView() {

		lblFrom = new Label();
		lblSubject = new Label();
		lblReplyTo = new Label();
		lblTO = new Label();
		lblCC = new Label();
		lblBCC = new Label();
		lblSendDate = new Label();
		lblAttachmentNumber = new Label();
		lblAttachmentFileNames = new Label();
		lblAttachmentFilePath = new Label();
		lblAttachmentFullFileName = new Label();

		attachments = new ArrayList<Button>();
		attachmentPanel = new HorizontalLayout();
		htmlArea = new HtmlLabel();
		htmlArea.setSizeFull();
		footer = new HorizontalLayout();

	}

	public void setMessageContent(String messageText) {
		htmlArea.setText(messageText);
	}

	public void init() {
		removeAll();
		attachmentPanel.removeAll();

		lblFrom.setText("");
		lblSubject.setText("");
		lblReplyTo.setText("");
		lblTO.setText("");
		lblCC.setText("");
		lblBCC.setText("");
		lblSendDate.setText("");
		lblAttachmentNumber.setText("");
		lblAttachmentFileNames.setText("");
		htmlArea.setText("");
	}

	public void refresh() {
		removeAll();
		if (!Strings.isNullOrEmpty(lblFrom.getText()))
			add(lblFrom);
		if (!Strings.isNullOrEmpty(lblSubject.getText()))
			add(lblSubject);
		if (!Strings.isNullOrEmpty(lblReplyTo.getText()))
			add(lblReplyTo);
		if (!Strings.isNullOrEmpty(lblTO.getText()))
			add(lblTO);
		if (!Strings.isNullOrEmpty(lblCC.getText()))
			add(lblCC);
		if (!Strings.isNullOrEmpty(lblBCC.getText()))
			add(lblBCC);
		add(lblSendDate);
		if (!Strings.isNullOrEmpty(lblAttachmentNumber.getText()))
			add(lblAttachmentNumber);
		if (!Strings.isNullOrEmpty(lblAttachmentFileNames.getText()))
			add(lblAttachmentFileNames);

		add(attachmentPanel);
		add(htmlArea);
		add(footer);
	}

	public Label getLblFrom() {
		return lblFrom;
	}

	public void setLblFrom(Label lblFrom) {
		this.lblFrom = lblFrom;
	}

	public Label getLblSubject() {
		return lblSubject;
	}

	public void setLblSubject(Label lblSubject) {
		this.lblSubject = lblSubject;
	}

	public Label getLblReplyTo() {
		return lblReplyTo;
	}

	public void setLblReplyTo(Label lblReply) {
		this.lblReplyTo = lblReply;
	}

	public Label getLblTO() {
		return lblTO;
	}

	public void setLblTO(Label lblTO) {
		this.lblTO = lblTO;
	}

	public Label getLblCC() {
		return lblCC;
	}

	public void setLblCC(Label lblCC) {
		this.lblCC = lblCC;
	}

	public Label getLblBCC() {
		return lblBCC;
	}

	public void setLblBCC(Label lblBCC) {
		this.lblBCC = lblBCC;
	}

	public Label getLblSendDate() {
		return lblSendDate;
	}

	public void setLblSendDate(Label lblSendDate) {
		this.lblSendDate = lblSendDate;
	}

	public Label getLblAttachmentNumber() {
		return lblAttachmentNumber;
	}

	public void setLblAttachmentNumber(Label lblAttachmentNumber) {
		this.lblAttachmentNumber = lblAttachmentNumber;
	}

	public Label getLblAttachmentFileNames() {
		return lblAttachmentFileNames;
	}

	public void setLblAttachmentFileNames(Label lblAttachmentFileNames) {
		this.lblAttachmentFileNames = lblAttachmentFileNames;
	}

	public String getRawMail() {
		return rawMail;
	}

	public void setRawMail(String rawMail) {
		this.rawMail = rawMail;
	}

	public Label getLblAttachmentFilePath() {
		return lblAttachmentFilePath;
	}

	public void setLblAttachmentFilePath(Label lblAttachmentFilePath) {
		this.lblAttachmentFilePath = lblAttachmentFilePath;
	}

	public Label getLblAttachmentFullFileName() {
		return lblAttachmentFullFileName;
	}

	public void setLblAttachmentFullFileName(Label lblAttachmentFullFileName) {
		this.lblAttachmentFullFileName = lblAttachmentFullFileName;
	}

	public HorizontalLayout getAttachmentPanel() {
		return attachmentPanel;
	}

	public void setAttachmentPanel(HorizontalLayout attachmentPanel) {
		this.attachmentPanel = attachmentPanel;
	}

}