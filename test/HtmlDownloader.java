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

public class HtmlDownloader {
    public static void main(String[] args) {
        String url = "http://localhost:1337/main/home";

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);
            
            HttpResponse response = httpClient.execute(getRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed: HTTP Error Code: "
                    + response.getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                response.getEntity().getContent()));

            String output;
            System.out.println("Output from server ...\n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            httpClient.getConnectionManager().shutdown();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.err.println("what the fuck is going on"); 
        }
    } 
}
