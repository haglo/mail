package org.mail.model.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Entity implementation class for Entity: PmailFolder
 *
 */
@Entity
@SuppressWarnings("all")
public class PmailFolder extends Superclass implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(unique = true)
	private String pfolderName;

	public String getPfolderName() {
		return pfolderName;
	}

	public void setPfolderName(String pfolderName) {
		this.pfolderName = pfolderName;
	}
}
