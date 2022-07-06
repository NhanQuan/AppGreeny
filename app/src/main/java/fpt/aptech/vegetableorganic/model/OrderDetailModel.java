package fpt.aptech.vegetableorganic.model;

import java.io.Serializable;
import java.util.List;

public class OrderDetailModel implements Serializable {
    boolean success;
    String message;
    List<OrderDetail> result;

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

    public List<OrderDetail> getResult() {
        return result;
    }

    public void setResult(List<OrderDetail> result) {
        this.result = result;
    }
}
