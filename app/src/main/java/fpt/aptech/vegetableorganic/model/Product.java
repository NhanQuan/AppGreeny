package fpt.aptech.vegetableorganic.model;



import java.io.Serializable;
import java.util.Date;


public class Product implements Serializable {
    private int product_id;
    private String description;
    private int discount;
    private String entered_date;
    private String price;
    private String product_image;
    private String product_name;
    private int quantity;
    private Boolean status;
    private int category_id;
    public boolean favorite;


    public Product()  {
    }

    public Product(int product_id, String description, int discount, String entered_date, String price, String product_image, String product_name, int quantity, Boolean status, int category_id, boolean favorite) {
        this.product_id = product_id;
        this.description = description;
        this.discount = discount;
        this.entered_date = entered_date;
        this.price = price;
        this.product_image = product_image;
        this.product_name = product_name;
        this.quantity = quantity;
        this.status = status;
        this.category_id = category_id;
        this.favorite = favorite;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setProduct_id(int product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getEntered_date() {
        return entered_date;
    }

    public void setEntered_date(String entered_date) {
        this.entered_date = entered_date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    @Override
    public String toString() {
        return "Product{" +
                "product_id=" + product_id +
                ", description='" + description + '\'' +
                ", discount=" + discount +
                ", entered_date=" + entered_date +
                ", price='" + price + '\'' +
                ", product_image='" + product_image + '\'' +
                ", product_name='" + product_name + '\'' +
                ", quantity=" + quantity +
                ", status=" + status +
                ", category_id=" + category_id +
                ", favorite=" + favorite +
                '}';
    }
}
