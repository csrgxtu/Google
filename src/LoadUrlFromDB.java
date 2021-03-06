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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class LoadUrlFromDB {
  // host is the MongoDB server host address
  private String host;

  // port is the port number of the server
  private int port;

  // dbName is the database name in the db server
  // default is Google
  private String dbName;

  // collectionName is the collection in the dbName
  // default is unvisited
  private String collectionName;

  // visitedCollection is the collection name of visited table
  private String visitedCollection;
  
  // unvisitedCollection is the collection name of unvisited table
  private String unvisitedCollection;
  
  // mongoClient is the client object for MongoDB
  private MongoClient mongoClient;

  // dbConnection is the database connection
  private DB dbConnection;

  // colConnection is the collection connection
  private DBCollection colConnection;

  // visitedConnection is the collection connection of visited
  private DBCollection visitedConnection;

  // unvisitedConnection is the collection connection of unvisited
  private DBCollection unvisitedConnection;

  // DB_CONF_FILE is the database.xml that contains some vars
  final private String DB_CONF_FILE = "./conf/database.xml";
  
 
  /**
   * constructor responsible init memeber properties
   *
   * @deprecated
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
    //System.out.println("INFO: Connected to the database");
  }

  /**
   * Constructor
   *
   */
  public LoadUrlFromDB() {
    // load conf first
    if (!this.loadDBConf()) {
      System.err.println("LoadUrlFromDB:Constructor Fatal Error Failed to"
        + " load database configuration file");
      System.exit(1);
    }
    
    try {
      this.mongoClient = new MongoClient(this.host, this.port);
      this.dbConnection = this.mongoClient.getDB(this.dbName);
      this.colConnection = this.dbConnection.getCollection(this.collectionName);
      this.visitedConnection = this.dbConnection.getCollection(
        this.visitedCollection);
      this.unvisitedConnection = this.dbConnection.getCollection(
        this.unvisitedCollection);
    } catch (Exception e) {
      System.err.println("LoadUrlFromDB:Constructor Fatal Error Failed to"
        + " connect to the database");
      System.err.println(e.getClass().getName() + ": "
        + e.getMessage());
      System.exit(1);
    }
  }
  
  /**
   * close used to close this client to the MongoDB server
   */
  public void close() {
    this.mongoClient.close();
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
    try {
      while (cursor.hasNext()) {
        // DBObject extends BSONObject, so use get get what u want
        urls.add(cursor.next().get("url").toString());
      }
    } catch (MongoException e) {
      return null;
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

    DBCursor cursor = this.unvisitedConnection.find().skip(start)
      .limit(offset);

    try {
      while (cursor.hasNext()) {
        dbObject = cursor.next();
        urls.add(dbObject.get("url").toString());

        this.unvisitedConnection.remove(dbObject);
        this.visitedConnection.insert(dbObject);
      }
    } catch (MongoException e) {
      return null; 
    }

    return urls;
  }

  /**
   * loadDBConf helper method that is used to load database conf
   *
   * @return boolean
   */
  private boolean loadDBConf() {
    try {
      File dbConfFile = new File(this.DB_CONF_FILE);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory
        .newInstance();

      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(dbConfFile);
      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("host");
      Node node = nList.item(0);
      Element element = (Element)node;
      this.host = element.getTextContent();
      
      nList = doc.getElementsByTagName("port");
      node = nList.item(0);
      element = (Element)node;
      this.port = Integer.parseInt(element.getTextContent());
      
      nList = doc.getElementsByTagName("dbName");
      node = nList.item(0);
      element = (Element)node;
      this.dbName = element.getTextContent();
      
      nList = doc.getElementsByTagName("colNameA");
      node = nList.item(0);
      element = (Element)node;
      this.visitedCollection = element.getTextContent();
      this.collectionName = this.visitedCollection;
      
      nList = doc.getElementsByTagName("colNameB");
      node = nList.item(0);
      element = (Element)node;
      this.unvisitedCollection = element.getTextContent();
      
      return true;
    } catch (Exception e) {
      //e.printStackTrace();
      return false;
    }
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
