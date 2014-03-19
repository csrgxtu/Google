//*******************************************************************
// Author: Archer Reilly
// File: InitDB.java
// Date: 19/Mar/2014
// Des: this class is used to read seed in seed.xml and inject the
//  url into the unvisited table, init the database automatically,
//  not manually
//
//  Dep: SaveUrlToDB
//
// Produced By CSRGXTU
//*******************************************************************

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class InitDB {
  // SEED_FILE is the seed file that contains the url to start
  final private String SEED_FILE = "./conf/seed.xml";
  
  // seedUrl is the url
  private String seedUrl = null;
  
  /**
   * Constructor
   *
   */
  public InitDB() {
     try {
      File seedFile = new File(this.SEED_FILE);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory
        .newInstance();

      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(seedFile);
      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("seedUrl");
      Node node = nList.item(0);
      Element element = (Element)node;
      //System.out.println(element.getTextContent());
      this.seedUrl = element.getTextContent();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  /**
   * init, init the database with seed url
   *
   * @return boolean
   */
  public boolean init() {
    SaveUrlToDB saveUrlToDB = new SaveUrlToDB();
    if (saveUrlToDB.removeCollections()) {
      if (saveUrlToDB.saveUrl(this.seedUrl)) {
        return true;
      } else {
        return false;
      }
    } else {
      return false;
    }
  }
  
  public String getSeedFile() {
    return this.SEED_FILE;
  }
  
  public String getSeedUrl() {
    return this.seedUrl;
  }
  
  public void setSeedUrl(String url) {
    this.seedUrl = url;
  }
  
  // testing
  /*public static void main(String[] args) {
    InitDB initDB = new InitDB();
    if (initDB.init()) {
      System.out.println("Successfully Done");
    } else {
      System.out.println("Failed Done");
    }
  }*/
}
