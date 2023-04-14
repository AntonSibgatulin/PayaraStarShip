package my.compary.crud;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

public class ExampleHTTPSRequest {


    public static void main(String... args) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        /*CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet("https://localhost:9998/");

        //Printing the method used
        System.out.println("Request Type: "+httpget.getMethod());

        //Executing the Get request
        HttpResponse httpresponse = httpclient.execute(httpget);

        Scanner sc = new Scanner(httpresponse.getEntity().getContent());

        //Printing the status line
        //System.out.println(httpresponse.getStatusLine());
        String string = "";
        while(sc.hasNext()) {
            string += sc.nextLine();
        }
        System.out.println(string);

         */

        /* Start of Fix */
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            public void checkClientTrusted(X509Certificate[] certs, String authType) {
            }

            public void checkServerTrusted(X509Certificate[] certs, String authType) {
            }

        }};

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /* End of the fix*/

        URL url = new URL("https://localhost:9998");
        URLConnection con = url.openConnection();
       /* Reader reader = new InputStreamReader(con.getInputStream());
        while (true) {
            int ch = reader.read();
            if (ch == -1)
                break;
            System.out.print((char) ch);
        }

        */
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String all = "";
        String pie = null;
        while ((pie = bufferedReader.readLine()) != null) {
            all += pie;
        }
        System.out.println(all);
    }
}
