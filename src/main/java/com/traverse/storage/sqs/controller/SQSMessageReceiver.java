
package com.traverse.storage.sqs.controller;

import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;
import com.traverse.storage.group.models.Message;

/**
 * @author pratikdas
 *
 */
@Slf4j
@Component
@EnableSqs
public class SQSMessageReceiver {
	@SqsListener(value = "${cloud.aws.end_point.uri}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveStringMessage(String message) {
//		log.info("Receiving message from SQS...\nReceived: Timestamp: {}\nAuthor: {}\nMessage: {}",
//				message.getTime(), message.getAuthor(), message.getMessage());
		System.out.println(message);
	}
}


