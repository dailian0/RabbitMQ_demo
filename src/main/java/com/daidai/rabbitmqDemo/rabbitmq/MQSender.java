package com.daidai.rabbitmqDemo.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
    AmqpTemplate amqpTemplate ;


	/**
	 * direct
	 * @param message
	 */
	public void send(Object message) {
		log.info("send message:"+message);
		amqpTemplate.convertAndSend(MQConfig.QUEUE, message);
	}

	/**
	 * Topic模式
	 * @param message
	 */
	public void sendTopic(Object message) {
		log.info("send topic message:"+message);
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key1", message+"1");
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", message+"2");
	}

	/**
	 * Fanout模式
	 * @param message
	 */
	public void sendFanout(Object message) {
		log.info("send fanout message:"+message);
		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", message);
	}

	/**
	 * Header模式
	 * @param message
	 */
	public void sendHeader(Object message) {
		String msg = beanToString(message);
		log.info("send fanout message:"+msg);
		MessageProperties properties = new MessageProperties();
		properties.setHeader("header1", "value1");
		properties.setHeader("header2", "value2");
		Message obj = new Message(msg.getBytes(), properties);
		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
	}

	private static <T> String beanToString(T value) {
		if(value == null) {
			return null;
		}
		Class<?> clazz = value.getClass();
		if(clazz == int.class || clazz == Integer.class) {
			return ""+value;
		}else if(clazz == String.class) {
			return (String)value;
		}else if(clazz == long.class || clazz == Long.class) {
			return ""+value;
		}else {
			return JSON.toJSONString(value);
		}
	}

	
	
}
