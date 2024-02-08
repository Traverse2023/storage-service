package com.traverse.storage.utils.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.traverse.storage.utils.exceptions.mongo.MongoConfigException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.beans.factory.annotation.Value;

import java.util.Collection;
import java.util.Collections;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {
    @Value("${spring.data.mongodb.uri}")
    private String URI;
    @Override
    protected String getDatabaseName() {
        return "Traverse";
    }

    @Value("${spring.data.mongodb.uri}")
    private String MONGO_URI;

    @Override
    public MongoClient mongoClient() {
        try {
            ConnectionString connectionString = new ConnectionString(MONGO_URI);

            MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                    .applyConnectionString(connectionString)
                    .build();
            return MongoClients.create(mongoClientSettings);
        } catch (Exception e) {
            throw new MongoConfigException(e.getMessage());
        }
    }

    @Override
    public Collection<String> getMappingBasePackages() {
        return Collections.singleton("com.traverse.storage.group.models.Group");
    }
}