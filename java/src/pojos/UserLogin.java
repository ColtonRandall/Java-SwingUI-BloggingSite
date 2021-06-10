package pojos;

public class UserLogin {

    private String usernameLogin;
    private String passwordLogin;

    public UserLogin(String username, String password) {
        this.usernameLogin = username;
        this.passwordLogin = password;
    }

    public String getUsername() {
        return usernameLogin;
    }

    public void setUsername(String username) {
        this.usernameLogin = username;
    }

    public String getPassword() {
        return passwordLogin;
    }

    public void setPassword(String password) {
        this.passwordLogin = password;
    }


}
