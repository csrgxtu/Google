//*******************************************************************
// Author: Archer Reilly
// Date: 28/Feb/2013
// File: WebPageDownload.java
// Des: a wrapper class for Apache's httpclient, use it
//  to download a url resource.
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

public class WebPageDownload {
    public static void main(String[] args) {
        String url = "http://blogxtu.zapto.org/";

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
        }
    } 
}
