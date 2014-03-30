//*******************************************************************
// Author: Archer Reilly
// Date: 25/Mar/2014
// File: UrlDBManager.java
// Des: this class is used to schedule the order of the operations
// client programm want to operate the Url Database. the main purpose
// is used to implement the transactions on MongoDB or distributed
// crawling. on distributed crawling, there will be many clients or
// threads operate the database, so it is necessery to gurentee the
// autom operation.
//
// Produced By CSRGXTU
//*******************************************************************

import java.net.*;
import java.io.*;

import java.util.ArrayList;


public class UrlDBManager {
  // port is the port number gonna to be opened on server
  private int port;
  
  // serverSocket
  private ServerSocket serverSocket;
  
  
  /**
   * Constructor
   *
   * @param int
   */
  public UrlDBManager(int port) {
    // check out the params
    // To-Do
    
    this.port = port;
    
    try {
      // only accept one connection each time, dont concurrent
      this.serverSocket = new ServerSocket(port, 1);
    } catch (IOException e) {
      System.out.println("UrlDBManager:Constructor Fatal Error Cant"
        + " Bind To Port: " + port);
      System.exit(1);
    }
  }
  
  /**
   * Construcotr
   */
  public UrlDBManager() {
  
  }
  
  /**
   * run
   * run the server, ready to accept connections and do other logic
   */
  public void run() {
    while (true) {
      try {
        System.out.println("Server Waiting Connection On Port: "
          + this.port);
        Socket server = this.serverSocket.accept();
        
        System.out.println("Client Connected: "
          + server.getRemoteSocketAddress());
        
        DataInputStream in = new DataInputStream(
          server.getInputStream());
        System.out.println(in.readUTF());
        
        DataOutputStream out = new DataOutputStream(
          server.getOutputStream());
        out.writeUTF("Welcome To Connected To Server "
          + server.getLocalSocketAddress());
        
        Thread.sleep(10000);
        server.close();
      } catch (IOException e) {
        e.printStackTrace();
        break;
      } catch (InterruptedException e) {
        e.printStackTrace();
        break;
      }
    }
  }
  
  /**
   * getUrls
   * used to get the urls from unvisited table in Google Database
   *
   * @param int
   * @param int
   * @return ArrayList<String>
   */
  private ArrayList<String> getUrls(int start, int offset) {
    return null;
  }
  
  /**
   * saveUrls
   * used to save urls into visited table in Google Database
   *
   * @param ArrayList<String>
   * @return boolean
   */
  private boolean saveUrls(ArrayList<String> urls) {
    return true;
  }
  
  public int getPort() {
    return this.port;
  }
  
  public void setPort(int port) {
  
  }
  
  // testing 
  public static void main(String[] args) {
    int port = Integer.parseInt(args[0]);
    
    UrlDBManager urlDBManager = new UrlDBManager(port);
    urlDBManager.run();
  }
}
