package com.github.apeshave;

import java.io.UnsupportedEncodingException;

/**
 * The Interface IKafkaProducer.
 */
public interface IKafkaProducer {

	/**
	 * This method sends the message to the Kafka queue / topic provided in the argument.
	 * The data should be wrapped around the KafkaWrapper to follow the consistency of the
	 * messages.
	 *
	 * @param data the data
	 * @param topic the topic
	 * @throws KafkaException the kafka exception
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 */
	void sendMessage(@SuppressWarnings("rawtypes") KafkaWrapper data, Class<?> clazz, String topic) throws KafkaException;	
	
	void sendMessage(String data, String topic) throws KafkaException;	
}
