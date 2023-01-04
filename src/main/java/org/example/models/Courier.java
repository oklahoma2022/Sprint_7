package org.example.models;

public class Courier {

    // Работа с JSON как с объектом  через отедльный класс
    private String login;
    private String password;
    private String firstName;

    public Courier(String login,String password,String firstName ){
        this.login=login;
        this.password = password;
        this.firstName= firstName;
    }

    // пустой класс для ресташура
    public Courier() {

    }

    // создаю геттеры и сетеры для удобства обращения
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
