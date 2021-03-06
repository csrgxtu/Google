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

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;


public class SaveUrlToDB {
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

  // visitedCollection is the visited collection name
  private String visitedCollection;
  
  // unvisitedCollection is the unvisited collection name
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

  // DB_CONF_FILE is the configuration file of the database
  final private String DB_CONF_FILE = "./conf/database.xml";
  
  
  /**
   * constructor responsible init memeber properties
   *
   * @deprecated
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
      this.visitedConnection = this.dbConnection.getCollection("visisted");
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
   * Constuctor
   *
   */
  public SaveUrlToDB() {
    if (!this.loadDBConf()) {
      System.err.println("SaveUrlToDB:Constructor Fatal Error Failed to"
        + " load database configuration file");
      System.exit(1);
    }
    
    try {
      this.mongoClient = new MongoClient(this.host, this.port);
      this.dbConnection = this.mongoClient.getDB(this.dbName);
      this.colConnection = this.dbConnection.getCollection(
        this.unvisitedCollection);
      this.visitedConnection = this.dbConnection.getCollection(
        this.visitedCollection);
      this.unvisitedConnection = this.dbConnection.getCollection(
        this.unvisitedCollection);
    } catch (Exception e) {
      System.err.println("SaveUrlToDB:Constructor Fatal Error Cant Work with"
        + " MongoDB");
      System.exit(1);
    }
  }

  /**
   * close is used to close this connection
   *
   */
  public void close() {
    this.mongoClient.close();
  }

  /**
   * saveUrl is used to save an url into the this.collectionName
   *
   * @param String
   * @return boolean
   */
  public boolean saveUrl(String url) {
    // check out the parameter
    if (url == "") {
      return false;
    }

    try {
      // before insert into unvisited table, check out if this url is
      // already visited or in the table
      if (isVisited(url)) {
        return false;
      }
      if (isUnvisited(url)) {
        return false;
      }
      
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
      if (urls[i] == "") {
        return false;
      }

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
   * saveUrls is used to save a lot of url into this.collectionName only
   * if the url isnt visited, or not in visited collection
   *
   * @param: String[]
   * @param: boolean
   * @return boolean
   */
  public boolean saveUrls(String[] urls, boolean flag) {
    // check out the parameter
    if (urls == null) {
      throw new IllegalArgumentException("SaveUrlToDB:saveUrls "
        + "parameter anit set properly");
    }

    for (int i = 0; i < urls.length; i++) {
      // before doing inserting, check url intergrety, for more info
      // check class UrlFilter
      UrlFilter urlFilter = new UrlFilter(urls[i]);
      /*if (!urlFilter.filter()) {
        System.out.println("Shit Shit Shit " + urls[i]);
        continue; // jump over untidy urls
      }*/
      if (urlFilter.filter()) {
        // is the url what i want
        System.out.println("Oh ... " + urls[i]);
      } else {
        System.out.println("Shit Shit Shit " + urls[i]);
        continue;
      }

      // before insert, check if url is already in visited, if so,
      // dont insert, check if url is already in unvisited, if so,
      // dont insert too
      if (this.isVisited(urls[i])) {
        continue;
      }
      if (this.isUnvisited(urls[i])) {
        continue;
      }
      try {
        BasicDBObject doc = new BasicDBObject("url", urls[i]);

        this.colConnection.insert(doc);
      } catch (MongoException e) {
        System.out.println("SaveUrlToDB:saveUrls " + e.getMessage());
        return false;
      }
    }

    return true;
  }

  /**
   * isVisited is a helper method work for saveUrls, is used to check
   * if a given url is visisted or not, check in visited collection
   *
   * @param: String
   * @return boolean
   */
  private boolean isVisited(String url) {
    // ckeck out the parameter
    if (url == null) {
      throw new IllegalArgumentException("SaveUrlToDB:isVisited "
        + "parameter anit set properly");
    }

    // build a query
    BasicDBObject query = new BasicDBObject("url", url);

    DBCursor cursor = this.visitedConnection.find(query);

    if (cursor.count() == 0) {
      return false;
    } else {
      return true;
    }
    
  }

  /**
   * isUnvisited is a helper method work for saveUrls, is used to check
   * if a given url is in unvisited tble
   *
   * @param String
   * @return boolean
   */
  private boolean isUnvisited(String url) {
    // check out the parameter
    if (url == null) {
      throw new IllegalArgumentException("SaveUrlToDB:isUnvisited "
        + "parameter anit set properly");
    }

    BasicDBObject query = new BasicDBObject("url", url);
    DBCursor cursor = this.unvisitedConnection.find(query);

    if (cursor.count() == 0) {
      return false;
    } else {
      return true;
    }
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

  /**
   * removeVisited remove the visited table
   *
   * @return boolean
   */
  private boolean removeVisited() {
    try {
      this.visitedConnection.drop();
      return true;
    } catch (MongoException e) {
      return false;
    }
  }
  
  /**
   * removeUnvisited remove the unvisited table
   *
   * @return boolean
   */
  private boolean removeUnvisited() {
    try {
      this.unvisitedConnection.drop();
      return true;
    } catch (MongoException e) {
      return false;
    }
  }
  
  /**
   * removeCollections remove all collections in Google Database
   *
   * @return boolean
   */
  public boolean removeCollections() {
    if (this.removeVisited()) {
      //return true;
    } else {
      return false;
    }
    
    if (this.removeUnvisited()) {
      return true;
    } else {
      return false;
    }
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
      e.printStackTrace();
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
