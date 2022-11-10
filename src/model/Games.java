package model;

public class Games {
    // Attributes region
    private int gamesID;
    private int productID;
    private String name;
    private float price;
    //end region

    // Constructors region

    public Games(){}

    public Games(int gamesID, int productID, String name, float price){
        this.gamesID = gamesID;
        this.productID = productID;
        this.name = name;
        this.price = price;
    }
    // end region

    // Getters and Setters region

    public int getGamesID() {
        return gamesID;
    }

    public void setGamesID(int gamesID) {
        this.gamesID = gamesID;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
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

    // end region
}
