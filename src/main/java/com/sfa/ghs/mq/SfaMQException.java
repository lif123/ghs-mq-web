package com.sfa.ghs.mq;

public class SfaMQException extends Exception {
	private static final long serialVersionUID = 1L;
	private String messageKey;
	private String[] messageArgs;

	public SfaMQException() {
		super();
	}

	public SfaMQException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public SfaMQException(String message) {
		super(message);
	}

	public SfaMQException(Throwable throwable) {
		super(throwable);
	}

	public SfaMQException(String messageKey, String message) {
		super(message);
		this.messageKey = messageKey;
	}

	public SfaMQException(String messageKey, String messageArgs, String message) {
		super(message);
		this.messageKey = messageKey;
		this.messageArgs = new String[] { messageArgs };
	}

	public SfaMQException(String messageKey, String[] messageArgs,
			String message) {
		super(message);
		this.messageKey = messageKey;
		this.messageArgs = messageArgs;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public String[] getMessageArgs() {
		return messageArgs;
	}
}
