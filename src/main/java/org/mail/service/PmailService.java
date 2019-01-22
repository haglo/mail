package org.mail.service;

import java.util.List;
import org.mail.model.entity.Pmail;
import org.mail.model.entity.PmailFolder;
import org.mail.model.repository.PmailRepository;
import org.mail.model.repository.PmailFolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PmailService {
	@Autowired
	private PmailRepository pmailRepository;

	@Autowired
	private PmailFolderRepository pmailFolderRepository;

	public List<Pmail> getMails() {
		List<Pmail> entries = pmailRepository.findAllByOrderByIdDesc();
		return entries;
	}

	public Pmail setMail(Pmail entry) {
		entry = pmailRepository.save(entry);
		return entry;
	}

	public List<Pmail> getMailsByFolder(PmailFolder folder) {
		List<Pmail> pmails = pmailRepository.findByPmailFolder(folder);
		return pmails;
	}
	
	public List<PmailFolder> getFolders() {
	    List <PmailFolder> pmailFolders = pmailFolderRepository.findAll();
	    return pmailFolders;
	  }

	public PmailFolder getFolderByFolderName(String folderName) {
	    PmailFolder pmailFolder = pmailFolderRepository.findByPfolderName(folderName);
	    return pmailFolder;
	  }

}
