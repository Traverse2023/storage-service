package com.traverse.storage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;


@SpringBootApplication
public class StorageApplication {
    public static void main(final String[] args) {
        SpringApplication.run(StorageApplication.class, args);
    }
}
