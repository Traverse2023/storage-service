package com.traverse.storage.utils.serializer;


import com.traverse.storage.utils.exceptions.serializer.InvalidTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.kms.KmsClient;

import java.util.Map;

/**
 * Implementation of {@link TokenSerializer} to serialize/deserialize
 * pagination token for List APIs.
 *
 * <p>It chians {@link JacksonTokenMapper}, {@link ExpirationTimestampSerializer}
 * and {@link EncryptionSerializer} to serialize/deserialize a DynamoDb start key
 * to a String token that is URL friendly.
 *
 * <p>Serialize flow:
 * DynamoDb start key -> Json string -> Json String with TTL -> base64 encoded cipher text (Token)
 *
 * <p>Deserialize flow:
 * Token -> Base64 decoded plaintext -> Json String with TTL -> Json String -> DynamoDb start key
 */
@Service
@Slf4j
public class PaginationTokenSerializer implements TokenSerializer<Map<String, AttributeValue>> {
    private final TokenSerializer<Map<String, AttributeValue>> dynamoDbStartKeySerializer;
    private final TokenSerializer<String> timeBasedTokenSerializer;
    private final TokenSerializer<String> encryptedTokenSerializer;

    /**
     * Construct PaginationTokenSerializer.
     */
    @Autowired
    public PaginationTokenSerializer (final KmsClient kms, @Value("${aws.kms.keyId}") String kmsKeyId) {

        this.dynamoDbStartKeySerializer = new JacksonTokenMapper();
        this.timeBasedTokenSerializer = new ExpirationTimestampSerializer();
        this.encryptedTokenSerializer = new EncryptionSerializer(kms, kmsKeyId);
    }

    @Override
    public Map<String, AttributeValue> deserialize(final String token)
            throws InvalidTokenException {

        String plaintext = encryptedTokenSerializer.deserialize(token);
        String json = timeBasedTokenSerializer.deserialize(plaintext);
        return dynamoDbStartKeySerializer.deserialize(json);
    }

    @Override
    public String serialize(final Map<String, AttributeValue> startKey) {
        String json = dynamoDbStartKeySerializer.serialize(startKey);
        String jsonWithTtl = timeBasedTokenSerializer.serialize(json);
        return encryptedTokenSerializer.serialize(jsonWithTtl);
    }

}
