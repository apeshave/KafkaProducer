package com.github.apeshave.kafkaproducer;

import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import com.github.apeshave.kafkaproducer.IKafkaProducer;

/**
 * The Class KafkaProducer used to send the message to the provided kafka queue.
 * It consists of 2 constructors which require special attention to the
 * java.util.Properties object which is to be passed while creation of this
 * object.
 */
public class KafkaProducer implements IKafkaProducer {

	/** The properties. */
	private Properties properties;

	/** The producer config. */
	private ProducerConfig producerConfig;

	/** The producer. */
	private Producer<String, String> producer;

	/**
	 * Instantiates a new kafka producer.
	 */
	public KafkaProducer() {
	}

	/**
	 * Instantiates a new kafka producer with given properties. Ideally this
	 * java.util.Properties should contain these properties:
	 * 
	 * metadata.broker.list, kafka.zookeeper.host.port, serializer.class,
	 * request.required.acks, kafka.zkRoot , etc.
	 * 
	 * @param properties
	 *            the properties for the initialization of KafkaProducer
	 */
	public KafkaProducer(Properties properties) {
		this.properties = properties;
	}

	/**
	 * Gets the properties.
	 * 
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * Sets the properties.
	 * 
	 * @param props
	 *            the new properties
	 */
	public void setProperties(Properties props) {
		this.properties = props;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.staples.csr.kafka.IKafkaProducer#sendMessage(com.staples.csr.kafka
	 * .KafkaWrapper, java.lang.String)
	 */
	 
	public void sendMessage(@SuppressWarnings("rawtypes") KafkaWrapper data,
			Class<?> clazz, String topic) throws KafkaException {

		try {
			// String xml = getXMLForBean(data);

			String xml = fromXml(clazz, data);

			if (null == producerConfig)
				producerConfig = new ProducerConfig(properties);

			if (null == producer)
				producer = new Producer<String, String>(producerConfig);

			KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(
					topic, xml);
			synchronized (producer) {
				producer.send(keyedMessage);
			}

		} catch (Exception e) {
			throw new KafkaException(e);
		}
	}

	/**
	 * Gets the XML for bean. The XML conversion is done using the
	 * org.simpleframework.xml.Serializer
	 * 
	 * @param data
	 *            the data
	 * @return the XML for bean
	 * @throws UnsupportedEncodingException
	 *             the unsupported encoding exception
	 * @throws KafkaException
	 *             the kafka exception
	 */
	@SuppressWarnings("unused")
	private String getXMLForBean(@SuppressWarnings("rawtypes") KafkaWrapper data)
			throws KafkaException {

		Marshaller jaxbMarshaller = null;

		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(KafkaWrapper.class);
			jaxbMarshaller = jaxbContext.createMarshaller();
		} catch (JAXBException e2) {
			throw new KafkaException(e2);
		}

		StringWriter sw = new StringWriter();

		// output pretty printed
		try {
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		} catch (PropertyException e1) {
			throw new KafkaException(e1);
		}

		try {
			jaxbMarshaller.marshal(data, sw);
		} catch (JAXBException e1) {

			throw new KafkaException(e1);
		}

		return sw.toString();
	}

	
	public void sendMessage(String data, String topic) throws KafkaException {
		try {

			if (null == producerConfig)
				producerConfig = new ProducerConfig(properties);

			if (null == producer)
				producer = new Producer<String, String>(producerConfig);

			KeyedMessage<String, String> keyedMessage = new KeyedMessage<String, String>(
					topic, data);
			synchronized (producer) {
				producer.send(keyedMessage);
			}

		} catch (Exception e) {
			throw new KafkaException(e);
		}

	}

	public static <T> String fromXml(Class<?> clazz, @SuppressWarnings("rawtypes") KafkaWrapper data)
			throws KafkaException {

		StringWriter sw = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance((new Class[] {
					KafkaWrapper.class, clazz }));
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			sw = new StringWriter();

			try {
				jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,
						true);
			} catch (PropertyException e1) {
				throw new KafkaException(e1);
			}

			jaxbMarshaller.marshal(data, sw);

		} catch (JAXBException e1) {
			throw new KafkaException(e1);
		}
		return sw.toString();
	}

}
