# Generic KafkaProducer in JAVA

This project helps the beginners to send the messages to kafka queue without any configuration changes.

Standard Configuration Required :

	1. Create a properties file with all the kafka queue details. (eg: "kafka.properties")
		
		metadata.broker.list=
		kafka.zookeeper.host.port=
		serializer.class=
		request.required.acks=
		kafka.zkRoot=
		
	2. Use these configuration settings to set up the KafkaProducer.
	
		I use spring to simplify the process.
		
		Inside your application context xml file, add these lines.
		
			<util:properties id="kafkaProperties" location="WEB-INF/kafka.properties" />
	
		<bean id="kafkaProducer" class="com.github.apeshave.KafkaProducer"
			p:properties-ref="kafkaProperties" lazy-init="false" scope="singleton"/>

			(You can simply instantiate the class as well.)
			
	3. Finally, use it. 
	
		kafkaProducer.sendMessage(new KafkaWrapper<WrappedClass>(toSend), WrappedClass.class, "TOPIC NAME");
