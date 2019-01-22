package org.mail.ui.imap;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.UIDFolder;
import javax.mail.internet.MimeMessage;

import org.mail.service.*;
import org.mail.ui.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vaadin.flow.spring.annotation.SpringComponent;

import org.mail.common.*;
import org.mail.common.PersistMail.MAIL_TYPE;
import org.mail.model.entity.Pmail;
import org.mail.model.repository.PmailRepository;

@SpringComponent
public class Imap implements Const {

	@Autowired
	PmailService pmailService;

	private Store store;
	private Folder imapFolder;
	private ExtractHeader extractHeader;
	private ExtractContent extractContent;
	private PersistMail persistMessage;
	private MailServer mailServer;

	public void readFromImap() {
		mailServer = new MailServer();

		try {
			Properties properties = new Properties();

			String imapHost = mailServer.getImapHost();
			String username = mailServer.getImapUsername();
			String password = mailServer.getImapPassword();
			Integer port = mailServer.getImapPort();
			boolean isSSL = mailServer.isImapSSL();

			properties.put("mail.imap.user", username);
			properties.put("mail.imap.host", imapHost);
			properties.put("mail.imap.port", port);
			properties.put("mail.imap.ssl.enable", isSSL);
			properties.put("mail.imap.auth", true);

			Session emailSession = Session.getDefaultInstance(properties);

			store = isSSL ? emailSession.getStore("imaps") : emailSession.getStore("imap");
			store.connect(imapHost, username, password);
			if (store.isConnected()) {
				System.out.println("Connect to Imap: true");
			}

			imapFolder = store.getFolder("INBOX");
			imapFolder.open(Folder.READ_ONLY);
			UIDFolder uidImapFolder = (UIDFolder) imapFolder;

			// retrieve the messages from the folder in an array
			Message[] messages = imapFolder.getMessages();

			List<Pmail> pmails = pmailService.getMails();
			List<Long> list = new ArrayList<Long>();
			for (Pmail mail : pmails) {
				list.add(mail.getPimapID());
			}

			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				if (list.contains(uidImapFolder.getUID(message))) {
					continue;
				}
				extractHeader = new ExtractHeader(message);
				extractContent = new ExtractContent(message, "" + uidImapFolder.getUID(message));
				persistMessage = new PersistMail();
				persistMessage.setImapID("" + uidImapFolder.getUID(message));
				persistMessage.saveMail(message, pmailService, MAIL_TYPE.IMAP);
			}
			// close the store and folder objects
			imapFolder.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}