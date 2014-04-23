//*******************************************************************
// Author: Archer Reilly
// Date: 21/Apr/2014
// File: JsonSearchResult.java
// Des: the class responsible build the json search result, need
// input and will generat json output.
//
// Produced By CSRGXTU
//*******************************************************************

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * <h1> Buil Json Data Of Search Result </h1>
 * This class is used to build the Json format of the Search Result
 * that will be used in the Google Project.
 *
 * @author Archer Reilly
 * @version 0.0.1
 * @since 21-Apr-2014
 */
public class JsonSearchResult {
  /**
   * The source of the data that will be used to build the Json data
   */
  private String[][] srcData;
  
  /**
   * The Json data that will hold the actual json data
   */
  private JSONObject jsonData;
  
  
  /**
   * Constructor with no input
   */
  public JsonSearchResult() {
    // TO-DO
  }
  
  /**
   * Constructor that accepts the input, initialize the properties of
   * this class.
   *
   * @param srcData the two dimensional array
   * @exception NullPointerException IllegalArgumentException
   */
  public JsonSearchResult(String[][] srcData) {
    // check the validation of the input
    if (srcData == null) {
      throw new NullPointerException("Error: srcData isnt set");
    }
    if (srcData.length <= 0) {
      throw new IllegalArgumentException("Error: srcData is empty");
    }
    if (srcData[0].length != 3) {
      throw new IllegalArgumentException("Error: srcData should be nx3 dimensional");
    }
    
    this.srcData = srcData;
  }
  
  /**
   * build a JSONObject from srcData[][] directly, a wrapper method
   * make buildJson buildJons work together
   *
   * @return JSONObject or null
   */
  public JSONObject run() {
    // check if srcData ready
    if (this.srcData == null) {
      throw new NullPointerException("Error: srcData isnt ready!");
    }
    
    int rows = this.srcData.length;
    int cols = this.srcData[0].length;
    String title = null;
    String link = null;
    String abstr = null;
    JSONObject[] tmpJsonArr = new JSONObject[rows];
    
    // build array of JSONObject
    for (int row = 0; row < rows; row++) {
      title = this.srcData[row][0];
      link = this.srcData[row][1];
      abstr = this.srcData[row][2];
      tmpJsonArr[row] = this.buildJson(title, link, abstr);
    }
    
    // build final JSONObject
    this.jsonData = this.buildJsons(tmpJsonArr);
    return this.jsonData;
  }
  
  /**
   * build a simple json data with title, link, abstract only
   *
   * @param title title element of the json
   * @param link link element of the json
   * @param abstract abstract element of the json
   * @return JSONObject
   * @exception NullPointerException
   */
  public JSONObject buildJson(String title, String link, String abstr) {
    if (title == null || link == null || abstr == null) {
      throw new NullPointerException("Error: params isnt set");
    }
    
    JSONObject tmpJson = new JSONObject();
    
    tmpJson.put("Title", title);
    tmpJson.put("Link", link);
    tmpJson.put("Abstract", abstr);
    
    return tmpJson;
  }
  
  /**
   * build a json which contains data with array of other jsons
   *
   * @param jsons the array of JSONObject
   * @return JSONObject
   * @exception NullPointerException IllegalArgumentException
   */
  public JSONObject buildJsons(JSONObject[] jsons) {
    if (jsons == null) {
      throw new NullPointerException("Error: params isnt set");
    }
    if (jsons.length <= 0) {
      throw new IllegalArgumentException("Error: params not valid");
    }
    
    JSONObject tmpJson = new JSONObject();
    JSONArray tmpJsonArray = new JSONArray();
    
    for (int i = 0; i < jsons.length; i++) {
      tmpJsonArray.add(jsons[i]);
    }
    
    tmpJson.put("Data", tmpJsonArray);
    
    return tmpJson;
  }
  
  /**
   * getter get srcData, get the original input
   *
   * @return String[][] two dimensional arrary
   */
  public String[][] getSrcData() {
    return this.srcData;
  }
  
  /**
   * setter set srcData, set the original input data
   *
   * @param srcData the two dimensional array
   * @return boolean
   */
  public boolean setSrcData(String[][] srcData) {
    return true;
  }
  
  /**
   * getter get jsonData, get the builded json data
   *
   * @return JSONObject return an JSONObject
   */
  public JSONObject getJsonData() {
    return this.jsonData;
  }
  
  /**
   * setter set jsonData, set the property jsonData
   *
   * @param json
   * @return boolean
   */
  public boolean setJsonData(JSONObject json) {
    return true;
  }
  
  /**
   * main method to test the methods in the class
   *
   * @param args Unused
   * @return void
   */
  public static void main(String[] args) {
    // prepare the data
    String title = "GNOME";
    String link = "http://www.gnome.org/";
    String abstr = "GNOME is a open source desktop environment";
    String[][] srcData = {
                            {title, link, abstr},
                            {title, link, abstr},
                            {title, link, abstr},
                          };
    
    JsonSearchResult JSR = new JsonSearchResult(srcData);
    
    JSONObject json = JSR.run();
    
    /*JSONObject[] jsons = new JSONObject[3];
    for (int i = 0; i < 3; i++) {
      jsons[i] = json;
    }
    
    JSONObject jsonResult = JSR.buildJsons(jsons);*/
    
    System.out.println(json);
  }
}
