//*******************************************************************
// Author: Archer Reilly
// Date: 17/Mar/2014
// File: Main.java
// Des: test the DomainFilter.java
//
// Produced By CSRGXTU
//*******************************************************************

import java.util.ArrayList;

/**
 * A, get urls from visited(unvisited) table
 * B, use DomainFilter check if it is alright
 * C, loop
 */
public class Main{
  public static void main(String[] args) {
    String host = "csrgxtu3";
    int port = 27017;
    
    DomainFilter domainFilter = new DomainFilter();
    for (int j = 0; j < 1000 ; j+=100) {
      LoadUrlFromDB loadUrlFromDB = new LoadUrlFromDB(host, port);
      ArrayList<String> urls = loadUrlFromDB.readUrls(j, 1000);
      
      // no urls available
      if (urls.size() == 0) {
        break;
      }
      
      for (int i = 0; i < urls.size(); i++) {
        System.out.println(urls.get(i));
        if (domainFilter.isFilterd(urls.get(i))) {
          System.out.println("Yes " + urls.get(i));
        } else {
          System.out.println("No " + urls.get(i));
        }
      }

      loadUrlFromDB.close();
    }
  } 
}
