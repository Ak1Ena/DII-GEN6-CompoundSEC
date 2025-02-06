package org.app.authenticator;

public class User {

    private final String username;
    private final String password;
    User(String username,String password){
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean authenticate(String username, String password){
        return this.username.equals(username) & this.password.equals(password);
    }
}
