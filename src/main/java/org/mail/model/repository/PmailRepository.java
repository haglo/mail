package org.mail.model.repository;

import java.util.List;

import org.mail.model.entity.Pmail;
import org.mail.model.entity.PmailFolder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PmailRepository extends JpaRepository<Pmail, Long> {

	public List<Pmail> findAllByOrderByIdDesc();
	public List<Pmail> findByPmailFolder(PmailFolder pmailFolder);;
 
}
