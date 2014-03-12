//*******************************************************************
// Author: Archer Reilly
// Date: 08/Mar/2014
// File: MongoDBTest.java
// Des: this file is used to test the mongo java driver
//
// Produced By CSRGXTU.
//*******************************************************************

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.WriteConcern;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import com.mongodb.ServerAddress;

import java.util.Arrays;

public class MongoDBJDBC {
    // host contains the host where MongoDB leaves
    private String host = "localhost";

    // port is the port num
    private int port = 27017;

    // mongoClient is the client object
    private MongoClient mongoClient = null;

    // mongoDB is the database object that represents a connection
    // to a database in MongoDB
    private DB mongoDB = null;


    /**
     * contructor responsible to initialize the host:port and db object
     *
     * @param host
     * @param port
     */
    public MongoDBJDBC(String host, int port) {
        // check out the parameter here

        this.host = host;
        this.port = port;

        //this.mongoClient = new MongoClient(this.host, this.port);
        try {
            this.mongoClient = new MongoClient("localhost", this.port);
            this.mongoDB = this.mongoClient.getDB("mydb");
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage());
        }
    }


    // getters and setters
    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    private MongoClient getMongoClient() {
        return this.mongoClient;
    }

    private DB getMongoDB() {
        return this.mongoDB;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private void setMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    private void setMongoDB(DB mongoDB) {
        this.mongoDB = mongoDB;
    }

    public static void main(String[] args) {
        MongoDBJDBC obj = new MongoDBJDBC("localhost", 27017);
    }
}
