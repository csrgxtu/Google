//*******************************************************************
// Author: Archer Reilly
// Date: 19/Mar/2014
// File: Main.java
// Des: use other classes and download a website into local disk
//
// Produced By CSRGXTU
//*******************************************************************

import java.security.MessageDigest;
import java.util.ArrayList;

/**
 * A, use InitDB init the database from seed.xml
 * B, check if the loop is over
 * C, use LoadUrlFromDB load url from unvisited table
 * D, foreach url loaded from database, use HtmlDownloader download
 *  the content.
 * D1, save the content into file
 * E, foreach downloaded content, use RetrieveHref parse content,
 *  and get the urls it contains
 * F, foreach parsed urls, use UrlFilter filter the url
 * G, foreach parsed urls, use DomainFilter filter the url
 * H, if E and F ok, then insert the url into unvisited table
 * I, loop through B to G.
 */
public class Main {
  
  public static void main(String[] args) throws Exception {
    // set some vars
    String host = "csrgxtu3";
    int port = 27017;
    
    // use InitDB init the database
    InitDB initDB = new InitDB();
    if (!initDB.init()) {
      System.err.println("Main:main Fatal Error In InitDB");
      System.exit(1);
    }
    
    // loop forever, check the loop over condition in it
    for (int i = 0; ; i++) {
      // use LoadUrlFromDB load url from unvisited
      LoadUrlFromDB loadUrlFromDB = new LoadUrlFromDB();
      ArrayList<String> retrievedUrls = loadUrlFromDB.retrieveUrls(0, 100);
      if (retrievedUrls == null) {
        System.err.println("Main:main Fatal Error When Retrieving urls from"
          + " unvisited table");
        System.exit(1);
      }
      if (retrievedUrls.size() == 0) {
        System.out.println("Main:main Warning No urls available in unvisited"
          + " table");
        System.exit(0);
      }
      loadUrlFromDB.close();
      
      // foreach url loaded from database, use HtmlDownloader download the
      // content
      for (int j = 0; j < retrievedUrls.size(); j++) {
        HtmlDownloader htmlDownloader = new HtmlDownloader(retrievedUrls
          .get(j));
        if (htmlDownloader.doRequest()) {
          // generate filename and use FileSaver save the requested content
          MessageDigest md = MessageDigest.getInstance("MD5");
          md.update(retrievedUrls.get(j).getBytes());
          byte byteData[] = md.digest();
          // convert the byte to hex format
          StringBuffer sb = new StringBuffer();
          for (int k = 0; k < byteData.length; k++) {
           sb.append(Integer.toString((byteData[k] & 0xff) + 0x100, 16).substring(1));
          }
 
          //System.out.println("Digest(in hex format):: " + sb.toString());
          String fileContent = htmlDownloader.getRequestHead()
            + htmlDownloader.getContent();
          FileSaver fileSaver = new FileSaver(fileContent,
            "./data/crawl/" + sb.toString() + ".html");
          if (fileSaver.save()) {
            System.out.println("Saved file: " + sb.toString());
          } else {
            System.err.println("Main:main[80] Fatal Error when saving file: "
              + sb.toString());
            System.exit(1);
          }
          
          // use RetrieveHref parse the content
          RetrieveHref retrieveHref = new RetrieveHref(htmlDownloader.getContent(),
            retrievedUrls.get(j));
          ArrayList<String> parsedLinks = retrieveHref.parseLinks();
          if (parsedLinks.size() == 0) {
            System.out.println("Warning[90]: parsedLinks equal 0");
            continue;
          }
          for (int l = 0; l < parsedLinks.size(); l++) {
            // use UrlFilter filter the links
            UrlFilter urlFilter = new UrlFilter(parsedLinks.get(l));
            if (!urlFilter.filter()) {
              System.out.println("Waring[97]: UrlFilter Failed: "
                + parsedLinks.get(l));
              continue;
            }
            
            // use DomainFilter filter the links too
            DomainFilter domainFilter = new DomainFilter();
            if (!domainFilter.isFilterd(parsedLinks.get(l))) {
              System.out.println("Warning[105]: DomainFilter Failed: "
                + parsedLinks.get(l));
              continue;
            }
            
            // parsed all test, then insert it into unvisted table
            SaveUrlToDB saveUrlToDB = new SaveUrlToDB();
            if (saveUrlToDB.saveUrl(parsedLinks.get(l))) {
              System.out.println("Unvisited Inserted: " + parsedLinks.get(l));
            }
            saveUrlToDB.close();
          }
          
        } else {
          System.err.println("Main:main[86] Warning cant do request on "
            + retrievedUrls.get(j));
          continue;
        }
      }
    }
  }
}
