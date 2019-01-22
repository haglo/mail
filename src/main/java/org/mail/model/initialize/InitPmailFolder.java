package org.mail.model.initialize;

import org.mail.model.entity.PmailFolder;
import org.mail.model.repository.PmailFolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class InitPmailFolder implements ApplicationRunner {

	@Autowired
	private PmailFolderRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		if (repository.findAll().size() == 0) {
			PmailFolder entity1 = new PmailFolder();
			entity1.setPfolderName("Inbox");
			repository.save(entity1);

			PmailFolder entity2 = new PmailFolder();
			entity2.setPfolderName("Sent");
			repository.save(entity2);

			PmailFolder entity3 = new PmailFolder();
			entity3.setPfolderName("Draft");
			repository.save(entity3);

			PmailFolder entity4 = new PmailFolder();
			entity4.setPfolderName("Template");
			repository.save(entity4);

			PmailFolder entity5 = new PmailFolder();
			entity5.setPfolderName("Trash");
			repository.save(entity5);

			PmailFolder entity6 = new PmailFolder();
			entity6.setPfolderName("Archive");
			repository.save(entity6);
		}
	}
}