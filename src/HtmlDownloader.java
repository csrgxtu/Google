//*******************************************************************
// Author: Archer Reilly
// Date: 02/Mar/2014
// File: HtmlDownloader.java
// Des: a wrapper class for Apache's httpclient, use it
//  to download a url resource. this class is mainly used to
//  download a html page.
//
// Produced By CSRGXTU
//*******************************************************************

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.*;
import org.apache.http.Header;

public class HtmlDownloader {
    /**
     * url hold the target resource you want to retrive
     */
    private String url = null;

    /**
     * response contains the response data
     */
    private HttpResponse response = null;

    /**
     * responseCode contains the return code of the http request
     */
    private int responseCode = 200;

    /**
     * httpClient is the client instance var
     */
    private HttpClient httpClient = null;

    /**
     * methodGet is the method instance var
     */
    private HttpGet methodGet = null;

    /**
     * content is the html source code
     */
    private String content = "";


    /**
     * constructor, initialize url, http client, http method
     * instance.
     *
     * @param url string
     */
    public HtmlDownloader(String url) {
        // First, chekc params
        if (url == null) {
            throw new IllegalArgumentException("Need Url as parameter");
        }
        
        // init
        this.url = url;
        this.httpClient = new DefaultHttpClient();
        this.methodGet = new HttpGet(this.url);
    }
    
    public void setUrl(String url) {
        this.url = url;
    }

    public void setResponse(HttpResponse response) {
        this.response = response;
    }

    public void setResponseCode(int code) {
        this.responseCode = code;
    }

    public String getUrl() {
        return this.url;
    }

    public HttpResponse getResponse() {
        return this.response;
    }

    public int getResponseCode() {
        return this.responseCode;
    }

    public String getContent() {
        return this.content;
    }

    /**
     * getRequestHead is used to get the head data of the request
     */
    public void getRequestHead() {
        Header[] headers = this.response.getAllHeaders();
        for (Header header : headers) {
            System.out.println(header.toString());
        }
    }

    /**
     * doRequest is used to send the request
     *
     */
    public int doRequest() {
        try {
            this.response = this.httpClient.execute(this.methodGet);
        
            if (this.response.getStatusLine().getStatusCode() != 200) {
                //throw new RuntimeException("Failed: HTTP Error Code: "
                 //   + this.response.getStatusLine().getStatusCode());
                System.err.println(this.response.getStatusLine().getStatusCode()
                  + " " + this.url);
                return this.response.getStatusLine().getStatusCode();
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                this.response.getEntity().getContent()));

            String output;
            //System.out.println("Output from server ...\n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
                output += "\n";
                this.content += output;
            }
        } catch (ClientProtocolException e) {
            //e.printStackTrace();
            System.err.println("A Client Protocol Exception");
        } catch (IOException e) {
            //e.printStackTrace();
            System.err.println("A IO Exception");
        } finally {
            this.httpClient.getConnectionManager().shutdown();
        }

        // request ok
        return 200;
    }
 
}
