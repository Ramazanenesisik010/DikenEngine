package com.emirenesgames.engine;

public class DikenEngineException extends Exception {
	private static final long serialVersionUID = 1L;

	public DikenEngineException() {
	}

	public DikenEngineException(String message) {
		super(message);
	}

	public DikenEngineException(Throwable cause) {
		super(cause);
	}

	public DikenEngineException(String message, Throwable cause) {
		super(message, cause);
	}

	public DikenEngineException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
