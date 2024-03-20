package com.traverse.storage.utils.serializer;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


@Getter
public class PaginationConfigProvider {

    @Value("${aws.kms.keyId}")
    public String keyId;

}
