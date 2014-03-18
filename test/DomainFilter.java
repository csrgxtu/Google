//*******************************************************************
// Author: Archer Reilly
// Date: 17/Mar/2014
// File: DomainFilter.java
// Des: this class is used to filter the domain url, sometimes i only
//  want to do the work with certain urls in a domain, not the whole
//  internet. this class should be work with a DomainFilter.xml file
//
// Produced By CSRGXTU.
//*******************************************************************

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainFilter {
  // DOMAIN_CONF_FILE is the configuration file
  final private String DOMAIN_CONF_FILE = "../conf/DomainFilter.xml";

  // filterPattern is the pattern that used to filter the url
  private String filterPattern = null;

  /**
   * constructor initialize the memeber variables
   */
  public DomainFilter() {
    try {
      File domainFilterFile = new File(this.DOMAIN_CONF_FILE);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory
        .newInstance();

      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(domainFilterFile);
      doc.getDocumentElement().normalize();

      NodeList nList = doc.getElementsByTagName("domainFilter");
      Node node = nList.item(0);
      Element element = (Element)node;
      //System.out.println(element.getTextContent());
      this.filterPattern = element.getTextContent();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public String getFilterPattern() {
    return this.filterPattern;
  }

  /**
   * isFilterd is used to test if the url is matched by the pattern
   *
   * @param String
   * @return boolean
   */
  public boolean isFilterd(String url) {
    // check out the parameter first
    if (url == null) {
      //throw new IllegalArgumentException("DomainFilter:isFilterd need url"
      //  + " parameter set!");
      return false;
    }

    Pattern domainPattern = Pattern.compile(this.getFilterPattern());
    Matcher domainMatcher = domainPattern.matcher(url);
    if (domainMatcher.find()) {
      return true;
    } else {
      return false;
    }
  }
 
  // testing
  public static void main(String[] args) {
    //String url = "http://blogxtu.zapto.org/fdjs/index.htm";
    String url = "http://www.xtu.edu.cn/index.html";
    DomainFilter filter = new DomainFilter();
    if (filter.isFilterd(url)) {
      System.out.println(url + " Yes");
    } else {
      System.out.println(url + " NO");
    }
  }
}
