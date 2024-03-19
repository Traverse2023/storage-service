package com.traverse.storage.utils.serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Map;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.traverse.storage.utils.exceptions.serializer.InvalidTokenException;
import com.traverse.storage.utils.jackson.AttributeValueDeserializer;
import com.traverse.storage.utils.jackson.AttributeValueSerializer;
import com.traverse.storage.utils.jackson.SdkBytesDeserializer;
import com.traverse.storage.utils.jackson.SdkBytesSerializer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;


/**
 * Implementation of {@link TokenSerializer} to serialize/deserialize
 * DynamoDb's pagination keys (LastEvaluatedKey and ExclusiveStartKey)
 * using Jackson.
 * See doc: <a href="https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Query.html#Query.Pagination">...</a>
 */
@RequiredArgsConstructor
public class JacksonTokenMapper implements TokenSerializer<Map<String, AttributeValue>> {
    private static final ObjectMapper OBJECT_MAPPER;

    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(AttributeValue.class, new AttributeValueDeserializer());
        module.addSerializer(AttributeValue.class, new AttributeValueSerializer());
        module.addDeserializer(SdkBytes.class, new SdkBytesDeserializer());
        module.addSerializer(SdkBytes.class, new SdkBytesSerializer());
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
        OBJECT_MAPPER = objectMapper;
    }

    @Override
    public Map<String, AttributeValue> deserialize(final String token)
            throws InvalidTokenException {
        if (StringUtils.isBlank(token)) {
            throw new InvalidTokenException("The token is blank.");
        }

        try {
            return OBJECT_MAPPER.readValue(token,
                    new TypeReference<Map<String, AttributeValue>>() {
                    });
        } catch (IOException e) {
            throw new InvalidTokenException(String.format(
                    "Failed to deserialize token %s from Json.", token), e);
        }
    }

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public String serialize(final Map<String, AttributeValue> startKey) {
        return OBJECT_MAPPER.writeValueAsString(startKey);
    }

}
