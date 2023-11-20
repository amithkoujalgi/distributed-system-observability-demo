package io.github.amithkoujalgi.demo.models.http;

public class UserAuthToken {
    private String token;

    public UserAuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
