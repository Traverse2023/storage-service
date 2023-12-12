
package com.traverse.storage.message;

import com.traverse.storage.models.Message;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


/**
 * @author pratikdas
 *
 */
@Slf4j
@Component
@EnableSqs
public class SQSMessageReceiver {
	@SqsListener(value = "${cloud.aws.end_point.uri}", deletionPolicy = SqsMessageDeletionPolicy.NEVER)
	public void receiveStringMessage(Message message) {
		log.info("Receiving message from SQS...{}", message.toString());
		System.out.println(message);
	}
}


