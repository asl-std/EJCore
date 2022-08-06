package ru.aslcraft.api.incubator.lib.exception;

/**
 * <p>DownloadException class.</p>
 *
 * @author ZooMMaX
 * @version $Id: $Id
 */
public class DownloadException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = -631043589295797332L;

	/**
	 * <p>Constructor for DownloadException.</p>
	 */
	public DownloadException() {}

	/**
	 * <p>Constructor for DownloadException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 */
	public DownloadException(String message) {
		super(message);
	}

	/**
	 * <p>Constructor for DownloadException.</p>
	 *
	 * @param message a {@link java.lang.String} object
	 * @param cause a {@link java.lang.Throwable} object
	 */
	public DownloadException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * <p>Constructor for DownloadException.</p>
	 *
	 * @param cause a {@link java.lang.Throwable} object
	 */
	public DownloadException(Throwable cause) {
		super(cause);
	}

}
