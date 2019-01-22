package org.mail.common;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.mail.service.PmailService;
import org.mail.ui.utils.Const;
import org.mail.ui.utils.I18n;
import org.mail.common.AIFile.FILE_TYPE;
import org.mail.model.entity.Pmail;
import org.mail.model.repository.PmailRepository;

public class PersistMail implements Const {
	private String imapID;
	private String smtpID;
	
	public void saveMail(Message message, PmailService service, MAIL_TYPE mt) {
		String tmp = "";
		String pathDef = null;
		Pmail pmail = new Pmail();

		if (mt.equals(MAIL_TYPE.IMAP)) {
			pathDef = getImapID();
			
			pmail.setPmailFolder(service.getFolderByFolderName("Inbox"));
			long lo = Long.parseLong(getImapID());
			pmail.setPimapID(lo);
		}

		if (mt.equals(MAIL_TYPE.SMTP)) {
			pathDef = getSmtpID();
			pmail.setPmailFolder(service.getFolderByFolderName("Sent"));
		}

		ExtractHeader extractHeader = new ExtractHeader(message);
		ExtractContent extractContent = new ExtractContent(message, pathDef);

		pmail.setPmessageID(getMessageId(message));

		tmp = "";
		for (int n = 0; n < extractHeader.getFrom().length; n++) {
			if (n == 0) {
				tmp = tmp + extractHeader.getFrom()[n];
			} else {
				tmp = tmp + ", " + extractHeader.getFrom()[n];
			}
		}
		pmail.setPfrom(tmp);

		pmail.setPsubject(extractHeader.getSubject());

		tmp = "";
		for (int n = 0; n < extractHeader.getTo().length; n++) {
			if (n == 0) {
				tmp = tmp + extractHeader.getTo()[n];
			} else {
				tmp = tmp + ", " + extractHeader.getTo()[n];
			}
		}
		pmail.setPrecipientTO(tmp);

		tmp = "";
		for (int n = 0; n < extractHeader.getCc().length; n++) {
			if (n == 0) {
				tmp = tmp + extractHeader.getCc()[n];
			} else {
				tmp = tmp + ", " + extractHeader.getCc()[n];
			}
		}
		pmail.setPrecipientCC(tmp);

		tmp = "";
		for (int n = 0; n < extractHeader.getBcc().length; n++) {
			if (n == 0) {
				tmp = tmp + extractHeader.getBcc()[n];
			} else {
				tmp = tmp + ", " + extractHeader.getBcc()[n];
			}
		}
		pmail.setPrecipientBCC(tmp);

		pmail.setPsendDate(extractHeader.getSendDate());
		pmail.setPreceiveDate(extractHeader.getReceiveDate());

		pmail.setPattachmentNumber(extractContent.getNumberOfAttachments());

		tmp = "";
		for (AIFile aif : extractContent.getAiFiles()) {
			if (aif.getFileType().equals(FILE_TYPE.INLINE))
				continue;
			if (tmp.isEmpty()) {
				tmp = tmp + aif.getFileName();
			} else {
				tmp = tmp + ATTACHMENT_DELIMITER + aif.getFileName();
			}
		}
		pmail.setPattachmentFileName(tmp);

		if (extractContent.getAiFiles().size() > 0)
			pmail.setPattachmentFilePath(extractContent.getAiFiles().get(0).getFilePath().toString());

		tmp = "";
		for (AIFile aif : extractContent.getAiFiles()) {
			if (aif.getFileType().equals(FILE_TYPE.INLINE))
				continue;
			if (tmp.isEmpty()) {
				tmp = tmp + aif.getFileFullName();
			} else {
				tmp = tmp + ATTACHMENT_DELIMITER + aif.getFileFullName();
			}
		}
		pmail.setPattachmentFileFullName(tmp);

		pmail.setPcontent(I18n.encodeToBase64(extractContent.getEmailContent()));

		pmail.setPmessage(convertMessageToString((MimeMessage) message));

		service.setMail(pmail);

	}

//	public void persistImap(Message message, EmailService service) {
//		String tmp = "";
//		ExtractHeader extractHeader = new ExtractHeader(message);
//		ExtractContent extractContent = new ExtractContent(message, getImapMessageID());
//		Pmail pmail = new Pmail();
//		pmail.setPimapUid(getImapMessageID());
//
//		pmail.setPmessagID(getSmtpMessageId(message));
//
//		pmail.setPfolder("Inbox");
//
//		tmp = "";
//		for (int n = 0; n < extractHeader.getFrom().length; n++) {
//			if (n == 0) {
//				tmp = tmp + extractHeader.getFrom()[n];
//			} else {
//				tmp = tmp + ", " + extractHeader.getFrom()[n];
//			}
//		}
//		pmail.setPfrom(tmp);
//
//		pmail.setPsubject(extractHeader.getSubject());
//
//		tmp = "";
//		for (int n = 0; n < extractHeader.getTo().length; n++) {
//			if (n == 0) {
//				tmp = tmp + extractHeader.getTo()[n];
//			} else {
//				tmp = tmp + ", " + extractHeader.getTo()[n];
//			}
//		}
//		pmail.setPrecipientTO(tmp);
//
//		tmp = "";
//		for (int n = 0; n < extractHeader.getCc().length; n++) {
//			if (n == 0) {
//				tmp = tmp + extractHeader.getCc()[n];
//			} else {
//				tmp = tmp + ", " + extractHeader.getCc()[n];
//			}
//		}
//		pmail.setPrecipientCC(tmp);
//
//		tmp = "";
//		for (int n = 0; n < extractHeader.getBcc().length; n++) {
//			if (n == 0) {
//				tmp = tmp + extractHeader.getBcc()[n];
//			} else {
//				tmp = tmp + ", " + extractHeader.getBcc()[n];
//			}
//		}
//		pmail.setPrecipientBCC(tmp);
//
//		pmail.setPsendDate(extractHeader.getSendDate());
//		pmail.setPreceiveDate(extractHeader.getReceiveDate());
//
//		pmail.setPattachmentNumber(extractContent.getNumberOfAttachments());
//
//		tmp = "";
//		for (AIFile aif : extractContent.getAiFiles()) {
//			if (aif.getFileType().equals(FILE_TYPE.INLINE))
//				continue;
//			if (tmp.isEmpty()) {
//				tmp = tmp + aif.getFileName();
//			} else {
//				tmp = tmp + ATTACHMENT_DELIMITER + aif.getFileName();
//			}
//		}
//		pmail.setPattachmentFileName(tmp);
//
//		pmail.setPattachmentFilePath(MAIL_ATTACHMENTS_PATH_ABSOLUT + "/" + getImapMessageID());
//
//		tmp = "";
//		for (AIFile aif : extractContent.getAiFiles()) {
//			if (aif.getFileType().equals(FILE_TYPE.INLINE))
//				continue;
//			if (tmp.isEmpty()) {
//				tmp = tmp + aif.getFileFullName();
//			} else {
//				tmp = tmp + ATTACHMENT_DELIMITER + aif.getFileFullName();
//			}
//		}
//		pmail.setPattachmentFileFullName(tmp);
//
//		pmail.setPcontent(I18n.encodeToBase64(extractContent.getEmailContent()));
//
//		pmail.setPmessage(convertMessageToString((MimeMessage) message));
//
//		service.getPmailDAO().create(pmail);
//
//	}
//
//	public void persistSmtp(Message message, EmailService service) {
//
//	}

	private String convertMessageToString(MimeMessage msg) {
		String result = "";

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			msg.writeTo(out);
			result = Base64.getMimeEncoder().encodeToString(out.toByteArray());
		} catch (IOException | MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	private String getMessageId(Message message) {
		String id = null;
		String[] idHeaders;

		try {
			idHeaders = message.getHeader("Message-ID");

			if (idHeaders != null && idHeaders.length > 0) {
				id = idHeaders[0];
			} else {
				idHeaders = message.getHeader("Message-Id");
				if (idHeaders != null && idHeaders.length > 0) {
					id = idHeaders[0];
				}
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return id;
	}


	public String getImapID() {
		return imapID;
	}

	public void setImapID(String imapID) {
		this.imapID = imapID;
	}


	public String getSmtpID() {
		return smtpID;
	}

	public void setSmtpID(String smtpID) {
		this.smtpID = smtpID;
	}


	public enum MAIL_TYPE {
		IMAP, SMTP;
	}

}
