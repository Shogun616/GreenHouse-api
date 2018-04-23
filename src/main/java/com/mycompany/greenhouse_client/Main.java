package com.mycompany.greenhouse_client;

import java.io.IOException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.Arrays;
import java.util.List;

public class Main {
    private static ClientConfig config = new DefaultClientConfig();
    private static Client client = Client.create(config);
    private static WebResource service = client.resource(
            UriBuilder.fromUri("http://localhost:8080/GreenHouse_Service").build());
    
    public static void main(String[] args) throws IOException {
         // getting XML data
        String xmlString = service.path("webresources/greenhouse_webservice.sensordata" )
                .accept(MediaType.APPLICATION_XML).get(String.class);
        System.out.println(xmlString);
        System.out.println();
        
        //Getting book objects
        Sensordata[] dataArray = service.path("webresources/greenhouse_webservice.sensordata")
                .accept(MediaType.APPLICATION_XML).get(Sensordata[].class);
        
        for (Sensordata b : dataArray){
            System.out.println("Data id: "+b.getId()
                    + " Temperatur: "+b.getTemperatur()
                    + "°C " +" Elförbrukning: "+b.getBelysning()
                    + " kWh "+"Elpris: "+b.getEl()+ " kr/kWh "
                    +"Luftfuktighet: "+b.getLuftfuktighet()
                    + " % "+"Tid: "+b.getTid()+" "
                    +"SektorId: " +b.getSektorId());
        }
        
        Sektorer[] sektorArray = service.path("webresources/greenhouse_webservice.sektorer")
                .accept(MediaType.APPLICATION_XML).get(Sektorer[].class);
        for (Sektorer t: sektorArray){
            System.out.println("Sektor: " +t.getSektor()
            + " Rad: " +t.getRad());
        }
        
        //Converting an array to List
        List<Sensordata> dataList = Arrays.asList(dataArray);
        List<Sektorer> sektorList = Arrays.asList(sektorArray);

        
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);

        Sensordata data = new Sensordata(1,50,2,2,35);
                                            
        ClientResponse response = service.path("webresources/DataDBService/greenhouse_webservice.sensordata/add")
                .accept(MediaType.APPLICATION_XML).post(ClientResponse.class, data);
        
        System.out.println("Response " + response.getEntity(String.class));
    }
    
    
}
