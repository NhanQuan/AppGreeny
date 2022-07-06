package fpt.aptech.vegetableorganic.model;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String nameOrEmail;
    private String password;

    public LoginRequest() {
    }

    public LoginRequest(String nameOrEmail, String password) {
        this.nameOrEmail = nameOrEmail;
        this.password = password;
    }

    public String getNameOrEmail() {
        return nameOrEmail;
    }

    public void setNameOrEmail(String nameOrEmail) {
        this.nameOrEmail = nameOrEmail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "nameOrEmail='" + nameOrEmail + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
