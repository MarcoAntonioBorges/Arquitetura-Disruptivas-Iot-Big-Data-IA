package br.fiap.iot.restful_example;

import java.util.UUID;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Path("mqttproxy")
public class MyMqttTest {
	
	private IMqttClient mqttClient; 
	private String lastMessage = null;
	
	public MyMqttTest() throws MqttException {
		try {
			String url = "tcp://iot.eclipse.org:1883";
			String clientId = UUID.randomUUID().toString();
			//Por padrão o Paho usa persistência em disco,
			//mas pode ter problema com permissão quando usado em um Webservice
			MqttClientPersistence persist = new MemoryPersistence();
			mqttClient = new MqttClient(url, clientId, persist);
			
			MqttConnectOptions options = new MqttConnectOptions();
			options.setAutomaticReconnect(true);
			options.setCleanSession(true);
			options.setConnectionTimeout(10);
			
			mqttClient.connect(options);
		} catch (MqttException e) {
			throw new RuntimeException(e);
		}
	}

	@POST @Path("publish/{topic}")
	@Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getCliente(@PathParam("topic") String topic, String message) throws MqttPersistenceException, MqttException {	
		topic = topic.replace('_', '/');
		if(mqttClient.isConnected()) {
			MqttMessage msg = new MqttMessage(message.getBytes());
	        msg.setQos(0);
	        msg.setRetained(false);
	        mqttClient.publish(topic,msg);
	        return "Message sent to topic: "+ topic;
		} else return "Client not connected!";
    }
	
	@GET @Path("waitmessage/{topic}")
	@Produces(MediaType.TEXT_PLAIN)
    public synchronized String getMessage(@PathParam("topic") String topic) throws MqttSecurityException, MqttException, InterruptedException {	
		lastMessage = null;
		mqttClient.subscribe(topic.replace('_', '/'), new IMqttMessageListener() {
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				synchronized (MyMqttTest.this) {
					lastMessage = message.toString();
					MyMqttTest.this.notifyAll();
				}
			}
		});
		this.wait();
		return lastMessage;
    }
	
}
