package org.example.models;

public class AuthCourier {
    private String login;
    private String password;

    public AuthCourier(String login, String password) {
        this.login=login;
        this.password = password;

    }



    // пустой класс для ресташура
    public  AuthCourier() {
    }


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
