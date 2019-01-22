package org.mail.ui.view;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.mail.component.DownloadLink;
import org.mail.model.entity.Pmail;
import org.mail.model.entity.PmailFolder;
import org.mail.service.PmailService;
import org.mail.ui.utils.Const;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.base.Strings;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceWriter;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class MailListView extends VerticalLayout implements Const {

	private static final long serialVersionUID = 1L;

	private MailDetailView detailView;
	private Grid<Pmail> grid;
	private ListDataProvider<Pmail> dataProvider;
	private Set<Pmail> selectedMails;
	private Pmail selectedMail;
	
	public MailListView(PmailService service, @Autowired MailMainView mainMailView) {
		setSizeFull();
		PmailFolder folder = service.getFolderByFolderName("Inbox");
		createView(folder, service, mainMailView);
		
	}
	

	public MailListView(PmailFolder folder, PmailService service, @Autowired
			MailMainView mainMailView) {
		setSizeFull();
		createView(folder, service, mainMailView);

	}
	
	private void createView(PmailFolder folder, PmailService service, MailMainView mainMailView) {
		detailView = new MailDetailView();
		

		List<Pmail> list = service.getMailsByFolder(folder);
		dataProvider = DataProvider.ofCollection(list);

		grid = new Grid<Pmail>();
		grid.setSizeFull();
		grid.setWidth("100%");
		grid.setSelectionMode(SelectionMode.MULTI);
		grid.setDataProvider(dataProvider);

		grid.addColumn(Pmail::getId).setHeader("ID").setResizable(true);
		grid.addColumn(Pmail::getPfrom).setHeader("From").setResizable(true);
		grid.addColumn(Pmail::getPsubject).setHeader("Subject").setResizable(true);
		grid.addColumn(p -> convertTimestamp(p.getPreceiveDate())).setHeader("Receive Date").setResizable(true).setId("receiveDate");

		grid.addSelectionListener(event -> {
			selectedMail = new Pmail();
			selectedMails = new HashSet<Pmail>();
			selectedMails = event.getAllSelectedItems();
			if (selectedMails.size() != 1) {
				detailView.setMessageContent("");
				detailView.init();
			} else {
				selectedMail = getSelectedMail(selectedMails);
				if (selectedMail != null) {
					detailView.init();
					if (!Strings.isNullOrEmpty(selectedMail.getPfrom()))
						detailView.getLblFrom().setText("Von " + selectedMail.getPfrom());
					if (!Strings.isNullOrEmpty(selectedMail.getPsubject()))
						detailView.getLblSubject().setText("Betreff " + selectedMail.getPsubject());
					if (!Strings.isNullOrEmpty(selectedMail.getPrecipientTO()))
						detailView.getLblTO().setText("An " + selectedMail.getPrecipientTO());
					if (!Strings.isNullOrEmpty(selectedMail.getPrecipientCC()))
						detailView.getLblCC().setText("CC " + selectedMail.getPrecipientCC());
					if (!Strings.isNullOrEmpty(selectedMail.getPrecipientBCC()))
						detailView.getLblBCC().setText("BCC " + selectedMail.getPrecipientBCC());

					if (selectedMail.getPattachmentNumber() > 0) {
						detailView.getLblAttachmentNumber()
								.setText("Number of Attachments " + selectedMail.getPattachmentNumber());
					}

					if (!Strings.isNullOrEmpty(selectedMail.getPattachmentFileName()))
						detailView.getLblAttachmentFileNames()
								.setText("Filename1 " + selectedMail.getPattachmentFileName());

					if (!Strings.isNullOrEmpty(selectedMail.getPattachmentFilePath()))
						detailView.getLblAttachmentFilePath()
								.setText("Filename2 " + selectedMail.getPattachmentFilePath());

					if (!Strings.isNullOrEmpty(selectedMail.getPattachmentFileFullName()))
						detailView.getLblAttachmentFullFileName()
								.setText("Filename3 " + selectedMail.getPattachmentFileFullName());

					if (!Strings.isNullOrEmpty(selectedMail.getPattachmentFileFullName())) {
						for (Anchor tmp : createAttachment(selectedMail.getPattachmentFileFullName())) {
							detailView.getAttachmentPanel().add(tmp);
						}
					}

					String tmp = decodeFromBase64(selectedMail.getPcontent());
					detailView.setMessageContent(tmp);

					byte[] byteDecodedEmail = Base64.getMimeDecoder().decode(selectedMail.getPmessage());
					String decodedEmail = new String(byteDecodedEmail);
					detailView.setRawMail(decodedEmail);

					detailView.refresh();
				}
			}
			// Important
			mainMailView.getEmailContentRightBar().addToSecondary(detailView);
		});

		add(grid);		
	}

	private Pmail getSelectedMail(Set<Pmail> selectedMails) {
		selectedMail = new Pmail();
		if (selectedMails.size() > 1) {
			Notification.show("Only one Item");
			return null;
		}
		if (selectedMails.size() < 1) {
			Notification.show("Exact one Item");
			return null;
		}
		if (selectedMails.size() == 1) {
			for (Pmail pmail : selectedMails) {
				selectedMail = pmail;
			}
		}
		return selectedMail;

	}

	private String convertTimestamp(String dateString) {
		if (dateString.isEmpty()) {
			return null;
		}
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm", Locale.GERMAN);
		ZonedDateTime dateTime = ZonedDateTime.parse(dateString, inputFormatter);
		String out = dateTime.format(outputFormatter);
		return out;
	}

//	private List<Button> createAttachment(String in) {
//		Button downloadButton;
//		List<Button> result = new ArrayList<Button>();
//
//		String tmp1 = "\\";
//		StringBuffer sb = new StringBuffer();
//		for (int i = 0; i < ATTACHMENT_DELIMITER.length(); i++) {
//			sb.append(tmp1);
//			sb.append(ATTACHMENT_DELIMITER.charAt(i));
//		}
//		String delimiter = new String(sb);
//
//		List<String> attachments = new ArrayList<String>(Arrays.asList(in.split(delimiter)));
//
//		for (String str : attachments) {
//			File file = new File(str);
//			downloadButton = new Button(file.getName(), e -> {
//			});
//			downloadButton.setIcon(VaadinIcon.CLOUD_DOWNLOAD.create());
//
//			Resource res = new FileResource(file);
//			FileDownloader fd = new FileDownloader(res);
//			fd.extend(downloadButton);
//			result.add(downloadButton);
//
//		}
//		return result;
//	}

	private List<Anchor> createAttachment(String in) {
		List<Anchor> result = new ArrayList<>();

		String tmp1 = "\\";
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < ATTACHMENT_DELIMITER.length(); i++) {
			sb.append(tmp1);
			sb.append(ATTACHMENT_DELIMITER.charAt(i));
		}
		String delimiter = new String(sb);

		List<String> attachments = new ArrayList<String>(Arrays.asList(in.split(delimiter)));

		for (String str : attachments) {
			File file = new File(str);
			DownloadLink db = new DownloadLink(file);
			result.add(db);
		}
		return result;
	}

//	private Anchor createDownloadLink(File file) {
////		try {
////			InputStream targetStream = new DataInputStream(new FileInputStream(file));
////		} catch (FileNotFoundException e) {
////			// TODO Auto-generated catch block
////			e.printStackTrace();
////		}
//
//		StreamResource sr = new StreamResource(file.getName(), (StreamResourceWriter) file);
//		Anchor downloadLink = new Anchor(sr, "Download");
//		downloadLink.getElement().setAttribute("download", true);
//		return downloadLink;
//	}

	public static String encodeToBase64(String token) {
		String converted;
		converted = Base64.getEncoder().encodeToString(token.getBytes());
		return converted;
	}

	public static String decodeFromBase64(String token) {
		String decoded;
		try {
			byte[] asBytes = Base64.getDecoder().decode(token);
			decoded = new String(asBytes);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return decoded;
	}

}