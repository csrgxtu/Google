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

import java.util.regex.Matcher;
import java.util.regex.Pattern;


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
   * constructor
   *
   * @param String
   * @param baseUrl
   */
  public RetrieveHref(String htmlStr, String baseUrl) {
    // check out the parameter
    if (htmlStr == null || baseUrl == null) {
      throw new IllegalArgumentException("RetrieveHref:Constructor"
        + " parameter arnt set properly!");
    }

    this.htmlStr = htmlStr;
    this.doc = Jsoup.parse(this.htmlStr, baseUrl);
  }

  /**
   * parseLinks is used to parse all hyper text links
   *
   * @return ArrayList<String>
   */
  public ArrayList<String> parseLinks() {
    ArrayList<String> res = new ArrayList<String>();
    String patternStr = "(https?://.*/.*#.*)";
    Pattern pattern = Pattern.compile(patternStr);

    Elements links = this.doc.select("a");
    for (Element link : links) {
      // jump over section url
      Matcher matcher = pattern.matcher(link.attr("abs:href"));
      if (matcher.find()) {
        continue;
      }
      res.add(link.attr("abs:href").toString());      
      System.out.println("\nlink : " + link.attr("abs:href"));
    }

    return res;
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
  /*public static void main(String[] args) {
    String url = "http://docs.oracle.com/javase/7/docs/api/java/io/PrintWriter.html";
    HtmlDownloader htmlDownloaderObj = new HtmlDownloader(url);
    htmlDownloaderObj.doRequest();
    String html = htmlDownloaderObj.getContent();

    RetrieveHref retrieveHrefObj = new RetrieveHref(html, url);
    retrieveHrefObj.parseLinks();
  }*/
}
