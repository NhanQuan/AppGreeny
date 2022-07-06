package fpt.aptech.vegetableorganic.model;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    private int userId;
    private String avatar;
    private String email;
    private String name;
    private String password;
    private Date register_date;
    private Boolean status;

    public User(int userId, String avatar, String email, String name, String password, Date register_date, Boolean status) {
        this.userId = userId;
        this.avatar = avatar;
        this.email = email;
        this.name = name;
        this.password = password;
        this.register_date = register_date;
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegister_date() {
        return register_date;
    }

    public void setRegister_date(Date register_date) {
        this.register_date = register_date;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public User() {
    }
}
