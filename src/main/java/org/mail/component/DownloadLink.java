package org.mail.component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.server.StreamResource;

public class DownloadLink extends Anchor {

	private static final long serialVersionUID = 1L;
	private String downloadLabel;
	private Anchor anchor; 

	public DownloadLink(File file) {
		downloadLabel = file.getName();
		anchor = new Anchor(getStreamResource(file.getName(), file), downloadLabel);
		anchor.getElement().setAttribute("download", true);
		anchor.setHref(getStreamResource(file.getName(), file));
		add(anchor);
	}

	public DownloadLink(File file, String downloadIdentifierText) {
		downloadLabel = file.getName();
		anchor = new Anchor(getStreamResource(file.getName(), file), downloadIdentifierText);
		anchor.getElement().setAttribute("download", true);
		anchor.setHref(getStreamResource(file.getName(), file));
		add(anchor);
	}

	public StreamResource getStreamResource(String filename, File content) {
		return new StreamResource(filename, () -> {
			try {
				return new ByteArrayInputStream(FileUtils.readFileToByteArray(content));
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		});
	}
}
