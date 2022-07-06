package fpt.aptech.vegetableorganic.model;

public class Menu {
    private int menu_id;
    private String menu_image;
    private String menu_name;

    public Menu(int menu_id, String menu_image, String menu_name) {
        this.menu_id = menu_id;
        this.menu_image = menu_image;
        this.menu_name = menu_name;
    }

    public Menu() {
    }


    public int getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_image() {
        return menu_image;
    }

    public void setMenu_image(String menu_image) {
        this.menu_image = menu_image;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }
}
