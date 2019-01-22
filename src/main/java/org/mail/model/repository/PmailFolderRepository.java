package org.mail.model.repository;

import java.util.List;

import org.mail.model.entity.PmailFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PmailFolderRepository extends JpaRepository<PmailFolder, Long> {

	PmailFolder findByPfolderName(String folderName);
	List<PmailFolder> findAll();

}