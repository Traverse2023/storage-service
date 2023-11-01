// package com.traverse.storage.sqs.controller;

// //write code to poll from SQS queue



/**
 * 
 */
package com.traverse.storage.sqs.controller;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Service;


import io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

/**
 * @author pratikdas
 *
 */
@Slf4j
@Service
public class MessageReceiver {
	
	@SqsListener(value = "NotificationsFIFOQUEUE.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS )
	public void receiveStringMessage(final String message, 
	  @Header("SenderId") String senderId) {
		log.info("message received {} {}",senderId,message);
	}
}

// // import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.beans.factory.annotation.Value;
// // import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
// import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
// // import org.springframework.messaging.support.MessageBuilder;
// import org.springframework.stereotype.Service;
// // import org.springframework.web.bind.annotation.GetMapping;
// // import org.springframework.web.bind.annotation.PathVariable;
// // import org.springframework.web.bind.annotation.RestController;

// @Service
// public class SQSController {

//     // @Autowired
//     // private QueueMessagingTemplate queueMessagingTemplate;

//     @Value("${cloud.aws.end-point.uri}")
//     private String endPoint;

//     // @GetMapping("/put/{msg}")
//     // public void putMessagedToQueue(@PathVariable("msg") String message) {
//     //     queueMessagingTemplate.send(endPoint, MessageBuilder.withPayload(message).build());
//     // }

//     @SqsListener(value="NotificationsFIFOQUEUE.fifo")
//     public void loadMessagesFromQueue(String message) {
//         System.out.println("Queue Messages: " + message);
//     }
// }
