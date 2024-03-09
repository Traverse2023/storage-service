package com.traverse.storage.dynamoModels;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Messages")
public class Message {

    @DynamoDBHashKey(attributeName = "pk")
    private String pk;

    @DynamoDBRangeKey(attributeName = "sk")
    private String sk;

    @DynamoDBAttribute(attributeName = "type")
    private String type;

    @DynamoDBAttribute(attributeName = "id")
    private String id;

    @DynamoDBAttribute(attributeName = "created")
    private Date created;

    @DynamoDBAttribute(attributeName = "sender")
    private String sender;

    @DynamoDBAttribute(attributeName = "recipient")
    private String recipient;

    @DynamoDBAttribute(attributeName = "senderPfp")
    private String senderPfp;

    @DynamoDBAttribute(attributeName = "text")
    private String text;

    @DynamoDBAttribute(attributeName = "media")
    private String media;

    @DynamoDBAttribute
    private Date updated;

}
