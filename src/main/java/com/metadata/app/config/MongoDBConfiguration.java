package com.metadata.app.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import java.util.Collection;
import java.util.Collections;

@Configuration
@ComponentScan("com.metadata")
public class MongoDBConfiguration extends AbstractMongoConfiguration {


    @Value("${mongodb.port:27017}")
    int port;

    @Override
    protected String getDatabaseName() {
        return "test";
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        return Collections.singletonList(MongoDBConfiguration.class.getPackage().getName());
    }

    @Override
    public MongoClient mongoClient() {
        MongoClientURI uri=new MongoClientURI("mongodb://localhost:27017");
        MongoClient mongoClient=new MongoClient(uri);
        return mongoClient;
    }

}
