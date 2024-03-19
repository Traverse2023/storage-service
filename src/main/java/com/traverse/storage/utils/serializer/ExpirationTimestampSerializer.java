package com.traverse.storage.utils.serializer;

import com.traverse.storage.utils.exceptions.serializer.InvalidTokenException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import java.time.Duration;
import java.time.Instant;
import java.time.format.DateTimeParseException;

/**
 * Implementation of {@link TokenSerializer} that adds time information
 * to ensure the token will expire.
 */

@AllArgsConstructor
public class ExpirationTimestampSerializer implements TokenSerializer<String> {
    static final String TIMESTAMP_DELIMITER = "&";
    private final Duration TOKEN_TIME_TO_LIVE = Duration.ofDays(1);

    @Override
    public String deserialize(final String token) throws InvalidTokenException {
        validateTimestamp(token);
        String decodedToken = StringUtils.substringBeforeLast(token, TIMESTAMP_DELIMITER);
        if (StringUtils.isBlank(decodedToken)) {
            throw new InvalidTokenException("The token is blank.");
        }
        return decodedToken;
    }

    @Override
    public String serialize(final String token) {
        StringBuilder tokenBuilder = new StringBuilder(token);
        tokenBuilder.append(TIMESTAMP_DELIMITER);
        tokenBuilder.append(Instant.now().toString());
        return tokenBuilder.toString();
    }

    private void validateTimestamp(final String tokenString) throws InvalidTokenException {
        String timestampString = StringUtils.substringAfterLast(tokenString, TIMESTAMP_DELIMITER);
        Instant timestamp;
        try {
            timestamp = Instant.parse(timestampString);
        } catch (DateTimeParseException e) {
            throw new InvalidTokenException(
                    String.format("Invalid timestamp string %s in token.", timestampString), e);
        }

        if (timestamp.plus(TOKEN_TIME_TO_LIVE).isBefore(Instant.now())) {
            throw new InvalidTokenException(
                    String.format("Token %s has expired after timeout limit %s.", timestamp,
                            TOKEN_TIME_TO_LIVE));
        }
    }
}
