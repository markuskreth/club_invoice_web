package de.kreth.clubinvoice.report;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

import org.apache.commons.io.FilenameUtils;

import de.kreth.clubinvoice.data.User;

public class Signature {

	private final User user;

	public Signature(User user) {
		super();
		this.user = Objects.requireNonNull(user);
	}

	public OutputStream createOutputStream(String fileName) throws IOException {
		File dir = new File("images");
		dir.mkdirs();
		return new FileOutputStream(new File(dir, user.getId()  + "." + FilenameUtils.getExtension(fileName)));
	}
	
	public boolean isSignatureImageExists() {
		File[] listFiles = new File("images").listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().startsWith(user.getId() + ".");
			}
		});
		return listFiles!= null && listFiles.length > 0;
	}

	/**
	 * Check with {@link #isSignatureImageExists()} existence first.
	 * @return
	 * @throws NullPointerException if image does not exist.
	 */
	public File getSignatureUrl() {
		File[] listFiles = new File("images").listFiles(new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().startsWith(user.getId() + ".");
			}
		});
		if (listFiles == null || listFiles.length == 0) {
			throw new NullPointerException("Image file does not exist");
		}
		return listFiles[0];
	}
}
