//
//package com.traverse.storage.sqs.controller;
//import com.traverse.storage.utils.PostRequestSender;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
//import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
//import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
//import org.springframework.stereotype.Component;
//import com.traverse.storage.group.GroupService;
//// import lombok.extern.slf4j.Slf4j;
//// import com.traverse.storage.group.models.Message;
//import org.json.JSONObject;
//import org.json.JSONArray;
//
//import java.util.Objects;
//
//// @Slf4j
//@Component
//@EnableSqs
//public class SQSMessageReceiver {
//	@Autowired
//	private GroupService groupService;
//	@SqsListener(value = "${cloud.aws.end_point.uri}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
//	public void receiveStringMessage(String message) {
////		log.info("Receiving message from SQS...\nReceived: Timestamp: {}\nAuthor: {}\nMessage: {}",
////				message.getTime(), message.getAuthor(), message.getMessage());
//		JSONObject jsonObject = new JSONObject(message);
//		System.out.println(jsonObject);
//		String task = jsonObject.getString("task");
//		if (Objects.equals(task, "createGroup")) this.createGroupOnMongo(jsonObject);
//	}
//
//
//	public void createGroupOnMongo(JSONObject group) {
//		System.out.println("Creating group on Mongo...");
//		String name = group.getString("groupName");
//		groupService.addGroup(name);
//	}
//}
//
//
