package io.gitub.amithkoujalgi.demo.models.http;

public class UserLoginStatus {
    private boolean status;

    public UserLoginStatus(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
