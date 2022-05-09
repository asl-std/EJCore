package ru.asl.api.incubator.lib.exception;

public class DownloadException extends Exception {
	/**
	 *
	 */
	private static final long serialVersionUID = -631043589295797332L;

	public DownloadException() {}

	public DownloadException(String message) {
		super(message);
	}

	public DownloadException(String message, Throwable cause) {
		super(message, cause);
	}

	public DownloadException(Throwable cause) {
		super(cause);
	}

}
