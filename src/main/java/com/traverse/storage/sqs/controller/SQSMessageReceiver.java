// package com.traverse.storage.sqs.controller;

// //write code to poll from SQS queue



/**
 * 
 */
package com.traverse.storage.sqs.controller;

import com.traverse.storage.group.models.Message;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pratikdas
 *
 */
@Slf4j
@Component
@EnableSqs
public class SQSMessageReceiver {
	
	@SqsListener(value = "NotificationsFIFOQUEUE.fifo"
			//, deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS
			)
	public void receiveStringMessage(Message message) {
		log.info("Receiving message from SQS...");
		System.out.println(message);
	}
}


