//*******************************************************************
// Author: Archer Reilly
// Date: 12/Mar/2014
// File: LoadUrlFromDB.java
// Des: this class is used to load urls from database into the client
//  programm, only load unvisited url, for visited no need to load it
//  into the programm
//
// Produced By CSRGXTU
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

import java.util.ArrayList;


public class LoadUrlFromDB {
  // host is the MongoDB server host address
  private String host = "localhost";

  // port is the port number of the server
  private int port = 27017;

  // dbName is the database name in the db server
  // default is Google
  private String dbName = "Google";

  // collectionName is the collection in the dbName
  // default is unvisited
  private String collectionName = "unvisited";

  // mongoClient is the client object for MongoDB
  private MongoClient mongoClient = null;

  // dbConnection is the database connection
  private DB dbConnection = null;

  // colConnection is the collection connection
  private DBCollection colConnection = null;

  // visitedConnection is the collection connection of visited
  private DBCollection visitedConnection = null;

  // unvisitedConnection is the collection connection of unvisited
  private DBCollection unvisitedConnection = null;

 
  /**
   * constructor responsible init memeber properties
   *
   * @param host
   * @param port
   */
  public LoadUrlFromDB(String host, int port) {
    // check the param first
    if (host == null || port == 0) {
      throw new IllegalArgumentException("LoadUrlFromDB:Constuctor"
        + " parameter arent set properly!");
    }

    this.host = host;
    this.port = port;
    this.dbName = "Google";
    this.collectionName = "unvisited";

    try {
      this.mongoClient = new MongoClient(this.host, this.port);
      this.dbConnection = this.mongoClient.getDB(this.dbName);
      this.colConnection = this.dbConnection.getCollection(this.collectionName);
      this.visitedConnection = this.dbConnection.getCollection("visited");
      this.unvisitedConnection = this.dbConnection.getCollection("unvisited");
    } catch (Exception e) {
      // Debug
      System.out.println("INFO: Failed to connect to the database");
      System.out.println(e.getClass().getName() + ": " 
        + e.getMessage());
    }

    // Debug
    System.out.println("INFO: Connected to the database");
  }

  /**
   * readUrls is used to read unvisited urls from this.dbName
   *
   * @param start
   * @param offset
   * @return ArrayList
   */
  public ArrayList<String> readUrls(int start, int offset) {
    // check the parameter

    ArrayList<String> urls = new ArrayList<String>();

    DBCursor cursor = this.colConnection.find().skip(start)
      .limit(offset);
    while (cursor.hasNext()) {
      // DBObject extends BSONObject, so use get get what u want
      urls.add(cursor.next().get("url").toString());
    }

    return urls;
  }

  /**
   * retrieveUrls retrive url from unvisited table and remove it
   * from unvisited table.
   *
   * @param start
   * @param offset
   * @return ArrayList
   */
  public ArrayList<String> retrieveUrls(int start, int offset) {
    //check the parameter

    ArrayList<String> urls = new ArrayList<String>();
    DBObject dbObject;

    DBCursor cursor = this.colConnection.find().skip(start)
      .limit(offset);

    while (cursor.hasNext()) {
      dbObject = cursor.next();
      urls.add(dbObject.get("url").toString());

      this.colConnection.remove(dbObject);
      this.visitedConnection.insert(dbObject);
    }

    return urls;
  }

  public String getHost() {
    return this.host;
  }

  public int getPort() {
    return this.port;
  }

  public String getDBName() {
    return this.dbName;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public void setDBName(String dbName) {
    this.dbName = dbName;
  }


}
