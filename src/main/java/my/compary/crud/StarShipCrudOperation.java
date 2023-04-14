package my.compary.crud;



import my.compary.database.DatabaseModel;
import my.compary.math.MathClass;

import my.compary.psixol.SpaceMarine;
import my.compary.psixol.StarShip;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;

import javax.net.ssl.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Scanner;


@Path("/starships")
public class StarShipCrudOperation {



    private  String baseUrl = null;
    private DatabaseModel databaseModel = new DatabaseModel();

    @GET
    @Produces(MediaType.APPLICATION_XML)
    @Path("/get/{id}")
    public Response getById(@PathParam("id") String id) {
        if (!MathClass.isNumeric(id)) {
            return Response.status(Response.Status.OK).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "Invalid ID supplied</text><code>400</code></action>").build();

        }
        StarShip starShip = databaseModel.starShip(Long.valueOf(id));
        if (starShip == null) {
            return Response.status(Response.Status.OK).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>SpaceMarine not found</text><code>404</code></action>").build();

        }
        return Response.status(Response.Status.OK).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<starship>"+starShip.toString()+"</starship>").build();
    }


    @POST
    @Path("/create")
    @Produces(MediaType.APPLICATION_XML)
    public Response create(String x){


        InputStream is = new ByteArrayInputStream(x.getBytes(StandardCharsets.UTF_8));
        JAXBContext jaxbContext;
        StarShip employee = null;
        try {
            jaxbContext = JAXBContext.newInstance(StarShip.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            employee = (StarShip) jaxbUnmarshaller.unmarshal(is);

        } catch (JAXBException e) {
            e.printStackTrace();
            return Response.status(400).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "Invalid input</text><code>400</code></action>").build();

        }


employee.setId(null);
        databaseModel.saveStarShip(employee);


        return Response.status(Response.Status.OK).entity("<starship>"+employee.toString()+"</starship>").build();
    }




    @Path("/{starship-id}/enter/{spacemarine-id}")
    @POST
    @Produces(MediaType.APPLICATION_XML)
    public Response enter(@PathParam("starship-id") String starShipId,@PathParam("spacemarine-id")String spaceMarineId){


        if(MathClass.isNumeric(starShipId)==false){
            return Response.status(400).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "Invalid input starShipId</text><code>400</code></action>").build();
        }
        if(MathClass.isNumeric(spaceMarineId)==false){
            return Response.status(400).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "Invalid input spaceMarineId</text><code>400</code></action>").build();
        }




        StarShip starShip = databaseModel.checkStarShip(Long.valueOf(starShipId));
        if(starShip==null){
            return Response.status(404).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "StarShip not found</text><code>404</code></action>").build();

        }



       // databaseModel.enter(spaceMarine,starShip);
        String result = null;
        try {
            result = httpRequest(new Long[]{starShip.getId(),Long.valueOf(spaceMarineId)}, "enter");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }




        return Response.status(200).entity(result).build();



    }




    @PUT
    @Path("/{starship-id}/unload-all")
    @Produces(MediaType.APPLICATION_XML)
    public Response unloadAll(@PathParam("starship-id")String starShipId){
        if(MathClass.isNumeric(starShipId)==false){
            return Response.status(400).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "Invalid input starShipId</text><code>400</code></action>").build();
        }
        StarShip starShip = databaseModel.starShip(Long.valueOf(starShipId));
        if(starShip==null){
            return Response.status(404).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "StarShip not found</text><code>404</code></action>").build();

        }
        //databaseModel.unloadAll(starShip);
        String result = null;
        try {
          result =   httpRequest(new Long[]{starShip.getId()}, "upload");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return Response.status(200).entity(result).build();

    }

    @PUT
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_XML)
    public Response update(@PathParam("id") String starShipId,String x){
        if(MathClass.isNumeric(starShipId)==false){
            return Response.status(400).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "Invalid input starShipId</text><code>400</code></action>").build();
        }

        InputStream is = new ByteArrayInputStream(x.getBytes(StandardCharsets.UTF_8));
        JAXBContext jaxbContext;
        StarShip employee = null;
        try {
            jaxbContext = JAXBContext.newInstance(StarShip.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            employee = (StarShip) jaxbUnmarshaller.unmarshal(is);

        } catch (JAXBException e) {
            e.printStackTrace();
            return Response.status(400).entity("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<action><text>\t\n" +
                    "Invalid input</text><code>400</code></action>").build();

        }



        databaseModel.updateStarShip(employee);


        return Response.status(Response.Status.OK).entity(employee.toString()).build();

    }





    public String httpRequest(Long[] id,String type) throws IOException, NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
       /* CloseableHttpClient httpclient = HttpClients.createDefault();

        HttpGet httpget = new HttpGet("https://localhost:8080/spacemarines/"+type+"/"+id[0]+"/"+(id.length==1?"":id[1]));

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
        return string;


        */


        if(baseUrl==null) {
            BufferedReader bufferedReader1 = new BufferedReader(new FileReader(new File("configure.json")));
            String all1 = "";
            String pie1 = null;
            while ((pie1 = bufferedReader1.readLine()) != null) {

                all1 += pie1;

            }
            baseUrl = "https://localhost:9596";
            try {
                JSONObject jsonObject = new JSONObject(all1);
                baseUrl = jsonObject.getString("host");

            } catch (JSONException e) {
                // e.printStackTrace(); ignore
            }

            if (baseUrl.endsWith("/")) {
                baseUrl = baseUrl.substring(0, baseUrl.length() - 2);

            }
        }


        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
            public void checkClientTrusted(X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(X509Certificate[] certs, String authType) { }

        } };

        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = new HostnameVerifier() {
            public boolean verify(String hostname, SSLSession session) { return true; }
        };
        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        /* End of the fix*/

        URL url = new URL(baseUrl+"/spacemarines/"+type+"/"+id[0]+"/"+(id.length==1?"":id[1]));
        System.out.println(url);
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
        while((pie = bufferedReader.readLine())!=null){
            all+=pie;
        }

        return all;

    }




}
