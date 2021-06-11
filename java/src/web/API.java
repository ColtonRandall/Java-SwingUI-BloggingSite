package web;

import pojos.User;
import pojos.UserLogin;
import util.JSONUtils;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;


public class API {

    // Variables
    private static API instance;
    private final CookieManager cookieManager;
    private final HttpClient client;
    private static final String BASE_URL = "http://localhost:3000";


    // Single object shared throughout he program (Singleton Pattern)
    public static API getInstance() {
        if (API.instance == null) {
            API.instance = new API();
        }
        return API.instance;
    }


    // Constructor
    private API() {
        this.cookieManager = new CookieManager();

        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(Duration.ofSeconds(10))
                .cookieHandler(this.cookieManager)
                .build();
    }


    // Login http method
    public boolean getUserLogin(UserLogin userLogin) throws InterruptedException, IOException {

        String loginJSON = JSONUtils.toJSON(userLogin);


        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/login"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(loginJSON));

        // Request
        HttpRequest request = builder.build();

        // Response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        java.util.List<HttpCookie> cookies = this.cookieManager.getCookieStore().get(URI.create(BASE_URL));


        // Authentication confirmation
        if (response.statusCode() == 204) {
            System.out.println("Authentication successful...");
            System.out.println(cookies);
            return true;
        } else {
            System.out.println("Authentication failed, please try again!");
            return false;
        }

    }

    // Logout http Method
    public void userLogout() throws InterruptedException, IOException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/logout"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody());

        // Request
        HttpRequest request = builder.build();

        // Response
        java.util.List<HttpCookie> cookies = this.cookieManager.getCookieStore().get(URI.create(BASE_URL));

        // Check if auth token has been cleared
        System.out.println(cookies);

    }


    // User list http Method
    public ArrayList<User> retrieveUserList() throws InterruptedException, IOException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/users"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody());

        // Request
        HttpRequest request = builder.build();

        // Response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        return (ArrayList<User>) JSONUtils.toList(json, User.class);
    }


    // Delete account http Method
    public Boolean deleteUser(int selectedRowUserId) throws InterruptedException, IOException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/api/users/:id"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.noBody());

        // Request
        HttpRequest request = builder.build();

        // Response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        JSONUtils.toObject(json, User.class);
        return null;
    }


}
