package web;

import pojos.User;
import pojos.UserLogin;
import util.JSONUtils;

import java.io.IOException;
import java.net.CookieManager;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class API {
    private static API instance;

    private static final String BASE_URL = "http://localhost:3000";


    // TODO SINGLETON PATTERN
    public static API getInstance() {
        if (instance == null) {
            instance = new API();
        }
        return instance;
    }

    private final CookieManager cookieManager;
    private final HttpClient client;

    private API() {
        this.cookieManager = new CookieManager();

        this.client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(Duration.ofSeconds(10))
                .cookieHandler(this.cookieManager)
                .build();
    }


    // Login
    public User getUserLogin(UserLogin userLogin) throws InterruptedException, IOException {

        String json = JSONUtils.toJSON(userLogin);

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/login"))
                .setHeader("Content-Type", "application/json")
                .setHeader("Accept", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(json));

        // Request
        HttpRequest request = builder.build();

        // Response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String responseJson = response.body();

        return JSONUtils.toObject(responseJson, User.class);

    }

    // Logout
    public void userLogout() throws InterruptedException, IOException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/logoutuser"))
                .setHeader("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody());

        // Request
        HttpRequest request = builder.build();

        // Response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        JSONUtils.toObject(json, User.class);
    }



    // Users
    public void retrieveUserInfo() throws InterruptedException, IOException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users"))
                .setHeader("Accept", "application/json")
                .method("GET", HttpRequest.BodyPublishers.noBody());

        // Request
        HttpRequest request = builder.build();

        // Response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        JSONUtils.toObject(json, User.class);
    }



    // Delete account
    public void deleteUser() throws InterruptedException, IOException {

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/users:id"))
                .setHeader("Accept", "application/json")
                .method("DELETE", HttpRequest.BodyPublishers.noBody());

        // Request
        HttpRequest request = builder.build();

        // Response
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        JSONUtils.toObject(json, User.class);
    }




//    public int getCallCount() {
//        List<HttpCookie> cookies = this.cookieManager.getCookieStore().get(URI.create(BASE_URL));
//        for (HttpCookie cookie : cookies) {
//            if (cookie.getName().equals("callCount")) {
//                return Integer.parseInt(cookie.getValue());
//            }
//        }
//        return 0;
//    }
}
