//*******************************************************************
// Author: Archer Reilly
// Date: 12/Mar/2014
// File: RetrieveHref.java
// Des: this class is used to retrieve hyper links from html source
//  code, all of them.
//
// Produced By CSRGXTU
//*******************************************************************
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class RetrieveHref {
  // htmlStr contains the original html source code
  private String htmlStr = null;

  // doc is the Jsoup typed document
  private Document doc = null;

  // title, the html page title
  private String title = null;

  // body, the html body content, parsed the html tag
  private String body = null;


  /**
   * constructor
   *
   * @param String
   */
  public RetrieveHref(String htmlStr) {
    // check out the parameter
    if (htmlStr == null) {
      throw new IllegalArgumentException("RetrieveHref:Constructor"
        + " parameter arnt set properly!");
    }

    this.htmlStr = htmlStr;
    this.doc = Jsoup.parse(this.htmlStr);
  }

  /**
   * parseLinks is used to parse all hyper text links
   *
   * @return ArrayList<String>
   */
  public ArrayList<String> parseLinks() {
    Elements links = this.doc.select("a");
    for (Element link : links) {
      //String tmplink = link.attr("href");
      String tmplink = link.absUrl("href");
      System.out.println("\nlink : " + tmplink);
    }

    return null;
  }

  public String getHtmlStr() {
    return this.htmlStr;
  }

  private Document getDoc() {
    return this.doc;
  }

  public String getTitle() {
    return this.title;
  }

  public String getBody() {
    return this.body;
  }

  public void setHtmlStr(String htmlStr) {
    this.htmlStr = htmlStr;
  }

  private void setDoc(Document doc) {
    this.doc = doc;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setBody(String body) {
    this.body = body;
  }

  // Debuging
  public static void main(String[] args) {
    String url = "http://www.debian.org/";
    HtmlDownloader htmlDownloaderObj = new HtmlDownloader(url);
    htmlDownloaderObj.doRequest();
    String html = htmlDownloaderObj.getContent();

    RetrieveHref retrieveHrefObj = new RetrieveHref(html);
    retrieveHrefObj.parseLinks();
  }
}
