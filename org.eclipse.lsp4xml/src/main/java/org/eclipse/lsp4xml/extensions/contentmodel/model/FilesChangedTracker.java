/*******************************************************************************
* Copyright (c) 2019 Red Hat Inc. and others.
* All rights reserved. This program and the accompanying materials
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v20.html
*
* Contributors:
*     Red Hat Inc. - initial API and implementation
*******************************************************************************/
package org.eclipse.lsp4xml.extensions.contentmodel.model;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * File changed tracker.
 * 
 * @author Angelo ZERR
 *
 */
public class FilesChangedTracker {

	private static final Logger LOGGER = Logger.getLogger(FilesChangedTracker.class.getName());

	private final List<FileChangedTracker> files;

	public FilesChangedTracker() {
		files = new ArrayList<>();
	}

	/**
	 * Add file URI to track
	 * 
	 * @param fileURI
	 */
	public void addFileURI(String fileURI) {
		try {
			files.add(new FileChangedTracker(Paths.get(new URI(fileURI))));
		} catch (URISyntaxException e) {
			LOGGER.log(Level.SEVERE, "Add file URI to track failed", e);
		}
	}

	/**
	 * Returns true if one file has changed and false otherwise.
	 * 
	 * @return true if one file has changed and false otherwise.
	 */
	public boolean isDirty() {
		for (FileChangedTracker dirtyFile : files) {
			if (dirtyFile.isDirty()) {
				return true;
			}
		}
		return false;
	}
}
