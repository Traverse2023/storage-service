
package com.traverse.storage.sqs.controller;
import com.traverse.storage.group.models.Group;
import com.traverse.storage.group.models.Message;
import com.traverse.storage.utils.PostRequestSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;
import com.traverse.storage.group.GroupService;
// import lombok.extern.slf4j.Slf4j;
// import com.traverse.storage.group.models.Message;
import org.json.JSONObject;  
import org.json.JSONArray;

import java.util.Objects;

// @Slf4j
@Component
@Slf4j
@EnableSqs
public class SQSMessageReceiver {
	@SqsListener(value = "${cloud.aws.end_point.uri}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void receiveStringMessage(Message message) {
//		log.info("Receiving message from SQS...\nReceived: Timestamp: {}\nAuthor: {}\nMessage: {}",
//				message.getTime(), message.getAuthor(), message.getMessage());
		log.info("Message received: {}", message.getMessage());
	}

	public void createGroupOnMongo(JSONObject group) {
		PostRequestSender postRequestSender = new PostRequestSender();
		System.out.println("Creating group on Mongo...");
		String name = group.getString("groupName");
		String body = String.format("{ \"name\": \"%s\" }", name);
		postRequestSender.sendPostRequest(body,"/api/v1/groups/createGroup");
	}
}


