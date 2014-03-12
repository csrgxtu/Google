//*******************************************************************
// Author: Archer Reilly
// Date: 12/Mar/2014
// File: SaveUrlToDB.java
// Des: this class is used to save urls to the database visited table
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


public class SaveUrlToDB {
  // host is the MongoDB server host address
  private String host = "localhost";

  // port is the port number of the server
  private int port = 27017;

  // dbName is the database name in the db server
  // default is Google
  private String dbName = "Google";

  // collectionName is the collection in the dbName
  // default is unvisited
  private String collectionName = "visited";

  // mongoClient is the client object for MongoDB
  private MongoClient mongoClient = null;

  // dbConnection is the database connection
  private DB dbConnection = null;

  // colConnection is the collection connection
  private DBCollection colConnection = null;

  
  /**
   * constructor responsible init memeber properties
   *
   * @param host
   * @param port
   */
  public SaveUrlToDB(String host, int port, String colName) {
    // check the param first
    if (host == null || port == 0 || colName == null) {
      throw new IllegalArgumentException("LoadUrlFromDB:Constuctor"
        + " parameter arent set properly!");
    }

    this.host = host;
    this.port = port;
    this.dbName = "Google";
    this.collectionName = colName;

    try {
      this.mongoClient = new MongoClient(this.host, this.port);
      this.dbConnection = this.mongoClient.getDB(this.dbName);
      this.colConnection = this.dbConnection.getCollection(this.collectionName);
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
   * saveUrl is used to save an url into the this.collectionName
   *
   * @param String
   * @return boolean
   */
  public boolean saveUrl(String url) {
    // check out the parameter

    try { 
      // build the basic mongodb document object
      BasicDBObject doc = new BasicDBObject("url", url);

      this.colConnection.insert(doc);
    } catch (MongoException e) {
      System.out.println("SaveUrlToDB: saveUrl " + e.getMessage());
      return false;
    }

    return true;
  }

  /**
   * saveUrls is used to save a lot of url into this.collectionName
   *
   * @param String[]
   * @return boolean
   */
  public boolean saveUrls(String[] urls) {
    // check out the parameter

    for (int i = 0; i < urls.length; i++) {
      try {
        BasicDBObject doc = new BasicDBObject("url", urls[i]);

        this.colConnection.insert(doc);
      } catch (MongoException e) {
        System.out.println("SaveUrlToDB: saveUrls " + e.getMessage());
        return false;
      }
    }

    return true;

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
   * retriveUrls retrive url from unvisited table and remove it
   * from unvisited table.
   *
   * @param start
   * @param offset
   * @return ArrayList
   */
  public ArrayList<String> retriveUrls(int start, int offset) {
    //check the parameter

    ArrayList<String> urls = new ArrayList<String>();
    DBObject dbObject;

    DBCursor cursor = this.colConnection.find().skip(start)
      .limit(offset);

    while (cursor.hasNext()) {
      dbObject = cursor.next();
      urls.add(dbObject.get("url").toString());

      this.colConnection.remove(dbObject);
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
