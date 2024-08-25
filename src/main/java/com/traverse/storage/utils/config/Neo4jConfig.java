package com.traverse.storage.utils.config;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author isfar
 *
 * This configuration file is to connect to neo4j
 */
@Configuration
public class Neo4jConfig {

    @Bean
    public Driver neo4jDriver() {
        //TODO retrieve this from env variables
        return GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "password"));
    }
}
