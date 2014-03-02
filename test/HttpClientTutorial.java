import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.io.*;

public class HttpClientTutorial {
    private static String url = "http://blogxtu.zapto.org/";

    public static void main(String[] args) {
        // Create an instace of the http client
        HttpClient client = new HttpClient();

        // Create an instance of the method
        GetMethod method = new GetMethod(url);

        // Provide a custom retry handler is nessasery
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
            new DefaultHttpClientRetryHandler(3, false));

        try {
            // execute the method
            int statusCode = client.execute(method);

            if (statusCode != HttpStatus.SC_OK) {
                System.err.println("Method failed: "
                    + method.getStatusLine());
            }

            // read response body
            byte[] responseBody = method.readResponseBody();

            // deal with the response
            System.out.println(new String(responseBody));
        } catch (HttpException e) {
            System.err.println("Fatal Protocol Violation: "
                + e.getMessage());
            e.printStackTrace();

        } catch (IOException e) {
            System.err.println("Fatal Transport Error: "
                + e.getMessage());
            e.printStackTrace();
        } finally {
            // release the connection
            method.releaseConnection();
        }
    }
}
