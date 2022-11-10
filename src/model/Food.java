package model;

public class Food {

    // Attributes region
    private int idFood;
    private int idcategory;
    private int idproduct;
    private float price;
    private String name;
    private double weight;
    // end region

    //Constructor region
    public Food(){}

    public Food(int foodID, int categoryID, int productID, String name, double weight, float price){
        this.idFood = foodID;
        this.idcategory = categoryID;
        this.idproduct = productID;
        this.name = name;
        this.weight = weight;
        this.price = price;
    }

    public Food(int foodID, int categoryID, String name, double weight, float price){
        this.idFood = foodID;
        this.idcategory = categoryID;
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.idproduct = 1;
    }

    public Food(int idFood, int categoryID, int productID, String name, float price){
        this.idFood = idFood;
        this.idcategory = categoryID;
        this.idproduct = productID;
        this.name = name;
        this.price = price;
    }

    public Food( int categoryID, int productID, Double weight, String name, float price){
        this.idcategory = categoryID;
        this.idproduct = productID;
        this.weight = weight;
        this.name = name;
        this.price = price;
    }
    // end region

    // Getters and Setters region

    public int getIdFood() {
        return idFood;
    }

    public void setIdFood(int idFood) {
        this.idFood = idFood;
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    //end region
}
