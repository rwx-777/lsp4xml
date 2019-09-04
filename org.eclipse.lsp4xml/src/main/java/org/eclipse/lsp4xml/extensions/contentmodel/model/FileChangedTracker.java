package org.eclipse.lsp4xml.extensions.contentmodel.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileChangedTracker {

	private static final Logger LOGGER = Logger.getLogger(FileChangedTracker.class.getName());

	private final Path file;
	private FileTime lastModified;

	public FileChangedTracker(Path file) {
		this.file = file;
		try {
			lastModified = Files.getLastModifiedTime(file);
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Get last modified time failed", e);
		}
	}

	public boolean isDirty() {
		try {
			if (!Files.exists(file)) {
				// This case occurs when user delete the XML Schema / DTD file
				return true;
			}
			FileTime currentLastMofied = Files.getLastModifiedTime(file);
			if (!currentLastMofied.equals(lastModified)) {
				lastModified = currentLastMofied;
				return true;
			}
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Get last modified time failed", e);
			return true;
		}
		return false;
	}

}
