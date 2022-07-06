package fpt.aptech.vegetableorganic.model;
import java.io.Serializable;

public class userCheckout implements Serializable {
    private String address;
    private String phone;

    public userCheckout() {
    }

    public userCheckout(String address, String phone) {
        this.address = address;
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
