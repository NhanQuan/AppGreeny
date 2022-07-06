package fpt.aptech.vegetableorganic.model;

import java.util.List;

public class Item {
   int order_detail_id;
    int product_id;
    int pricedetail;
    int quantitydetail;
    String product_name;
    int quantity;
    String product_image;

    public int getPricedetail() {
        return pricedetail;
    }

    public void setPricedetail(int pricedetail) {
        this.pricedetail = pricedetail;
    }

    public int getQuantitydetail() {
        return quantitydetail;
    }

    public void setQuantitydetail(int quantitydetail) {
        this.quantitydetail = quantitydetail;
    }

    public int getOrder_detail_id() {
        return order_detail_id;
    }

    public void setOrder_detail_id(int order_detail_id) {
        this.order_detail_id = order_detail_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
}
