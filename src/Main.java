//*******************************************************************
// Author: Archer Reilly
// Date: 12/Mar/2014
// File: Main.java
// Des: Main program that make all the tools work together
//
// Produced By CSRGXTU
//*******************************************************************

import java.util.ArrayList;

/**
 * A, retrieve url from unvisited by LoadUrlFromDB
 * B, download the html page by HtmlDownloader
 * C, parse the html page by RetrieveHref
 * D, save the url into unvisited by SaveUrlToDB
 * F, loop the upper steps
 */ 
public class Main {

  public static void main(String[] args) {
    String host = "localhost";
    int port = 27017;
    ArrayList<String> urls;

    // retrieve url from unvisited collection
    LoadUrlFromDB loadUrlDBObj = new LoadUrlFromDB(host, port);
    urls = loadUrlDBObj.readUrls(0, 10);
    
    /*for (int i = 0; i < urls.size(); i++) {
      System.out.println(urls.get(i));
    }*/

    // download the html page
    for (int i = 0; i < urls.size(); i++) {
      System.out.println("Requesting " + urls.get(i));
      HtmlDownloader htmlDownloaderObj = new HtmlDownloader(
        urls.get(i));

      htmlDownloaderObj.doRequest();

      String res = htmlDownloaderObj.getContent();

      // parse the html by RetrieveHref
      RetrieveHref retrieveHrefObj = new RetrieveHref(res);
      ArrayList<String> links = retrieveHrefObj.parseLinks();
      for (int j = 0; j < links.size(); j++) {
        System.out.println(links.get(j));
      }
      //System.out.println(res);
    }

     
  } 
}
