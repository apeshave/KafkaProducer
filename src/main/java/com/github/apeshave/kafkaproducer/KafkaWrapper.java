package com.github.apeshave.kafkaproducer;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * The Class KafkaWrapper is used to wrap any kind of entity with a common structure to use.
 * The class to be wrapped must have annotations javax.xml.
 *
 * @param <T> the generic type
 */
@XmlRootElement
public class KafkaWrapper<T> {

	/** The data to be wrapped. */
	@XmlElement(name = "data")
	T data;
	
	/** The type of the data which is being wrapped. */
	@XmlElement(name = "type")
	String type;
	
	/**
	 * Instantiates a new kafka wrapper.
	 *
	 * @param data the data
	 */
	
	
	public KafkaWrapper(T data){
		this.data = data;
		
		if(data != null)
			this.type = data.getClass().getName();
		else
			this.type = null;
	}

	public KafkaWrapper() {
	}

	/**
	 * Gets the data.
	 *
	 * @return the data
	 */
	public T getData() {
		return data;
	}

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}

}
