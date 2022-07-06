package fpt.aptech.vegetableorganic.model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
    boolean success;
    String message;
    ArrayList<User> result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<User> getResult() {
        return result;
    }

    public void setResult(ArrayList<User> result) {
        this.result = result;
    }
}
