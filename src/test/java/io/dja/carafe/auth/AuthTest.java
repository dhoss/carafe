package io.dja.carafe.auth;


import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;

import static java.net.http.HttpClient.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


import org.junit.jupiter.api.Test;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.integration.ClientAndServer.startClientAndServer;
public class AuthTest {
    
    private ClientAndServer mockServer;
    
    
    HttpClient client = newHttpClient();
    
    
    @BeforeEach
    public void before() {
        mockServer =
                startClientAndServer(10000);
        mockServer
                .when(
                        request()
                                .withMethod("POST")
                                .withPath("/v3/oauth/request")
                                // TODO: mock out x-www-form-urlencoded as well
                                .withBody("{\"consumer_key\":\"1234-abcd1234abcd1234abcd1234\",\n" +
                                        "\"redirect_uri\":\"pocketapp1234:authorizationFinished\"}")
                )
                .respond(
                        response()
                                .withStatusCode(200)
                                .withHeader("Content-Type","application/json")
                                .withBody("{\"code\":\"dcba4321-dcba-4321-dcba-4321dc\"}")
                );
        
    }
    
    @AfterEach
    public void cleanup() {
        mockServer.stop();
    }
    
    @Test
    public void postToAuth() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:10000/v3/oauth/request"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"consumer_key\":\"1234-abcd1234abcd1234abcd1234\",\n" +
                        "\"redirect_uri\":\"pocketapp1234:authorizationFinished\"}"))
                .build();
    
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals("{\"code\":\"dcba4321-dcba-4321-dcba-4321dc\"}", response.body());
    }
    
    
}
