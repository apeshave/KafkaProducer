package com.github.apeshave.kafkaproducer;

import java.util.Calendar;
import java.util.Date;

/**
 * The Class KafkaException.
 */
public class KafkaException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -2429361820628113847L;

	/**
	 * Instantiates a new kafka exception.
	 */
	public KafkaException() {
		super(getTimestamp());
	}

	public KafkaException(Exception e){
		super(e);
	}
	
	/**
	 * Instantiates a new kafka exception.
	 *
	 * @param message the message
	 */
	public KafkaException(String message) {
		super(getTimestamp() + message);
	}

	/**
	 * Instantiates a new kafka exception.
	 *
	 * @param message the message
	 * @param throwable the throwable
	 */
	public KafkaException(String message, Throwable throwable) {
		super(getTimestamp() + message, throwable);
		super.setStackTrace(throwable.getStackTrace());
	}

	/**
	 * Gets the timestamp.
	 *
	 * @return the timestamp
	 */
	private static String getTimestamp() {
		Calendar calendar = Calendar.getInstance();
		Date date = new Date(calendar.getTimeInMillis());
		return "\n*************************************************************\nException in sending Kafka message on "
				+ date
				+ "\n*************************************************************\n";

	}

}
