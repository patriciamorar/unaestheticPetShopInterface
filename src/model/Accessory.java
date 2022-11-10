package model;

public class Accessory {
    //Attributes region
    private int idAccessory;
    private int idcategory;
    private int idproduct;
    private String name;
    private float price;
    // end region

    // Constructors region
    public Accessory(){}

    public Accessory(int accessoryID, int categoryID, int productID, String name, float price){
        this.idAccessory = accessoryID;
        this.idcategory = categoryID;
        this.idproduct = productID;
        this.name = name;
        this.price = price;
    }

    public Accessory(int accessoryID, int categoryID, int productID, String name){
        this.idAccessory = accessoryID;
        this.idcategory = categoryID;
        this.idproduct = productID;
        this.name = name;
    }

    public Accessory(int categoryID, int productID, String name, float price){
        this.idcategory = categoryID;
        this.idproduct = productID;
        this.name = name;
        this.price = price;
    }
    // end region

    // Getters and Setters region

    public int getIdAccessory() {
        return idAccessory;
    }

    public void setIdAccessory(int idAccessory) {
        this.idAccessory = idAccessory;
    }

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    public int getIdproduct() {
        return idproduct;
    }

    public void setIdproduct(int idproduct) {
        this.idproduct = idproduct;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    //end region

}
