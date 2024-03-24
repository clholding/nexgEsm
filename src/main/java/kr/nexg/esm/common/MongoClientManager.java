package kr.nexg.esm.common;

import javax.annotation.PreDestroy;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

public class MongoClientManager {
 
	private final MongoClient mongoClient;

    public MongoClientManager(String host, String port, String user, String password) {
    	String connectionString = "mongodb://" + host + ":" + port;
    	if(!"".equals(user) && !"".equals(password)) {
    		connectionString = "mongodb://" + user + ":" + password + "@" + host + ":" + port;
    	}
        ConnectionString connString = new ConnectionString(connectionString);
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .build();
        this.mongoClient = MongoClients.create(settings);
    }
    
    public MongoClient getMongoClient() {
    	return this.mongoClient;
    }

    @PreDestroy
    public void close() {
        if (mongoClient != null) {
        	mongoClient.close();
        }
    }
}

