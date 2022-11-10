package model;

public class Animal {
    // Attributes region
    private int idanimal;
    private int idcategory;
    private String status;
    private String breed;
    //end region

    // Constructor region
    public Animal(){}

    public Animal(int animalID, int categoryID, String status, String breed){
        this.idanimal = animalID;
        this.idcategory = categoryID;
        this.status = status;
        this.breed = breed;

    }

    public Animal(int animalID, int categoryID, String status){
        this.idanimal = animalID;
        this.idcategory = categoryID;
        this.status = status;

    }

    public Animal(int idcategory, String status, String breed) {
        this.idcategory = idcategory;
        this.status = status;
        this.breed = breed;
    }

    //end region

    //Getters and Setters region
    public int getIdanimal() {
        return idanimal;
    }

    public void setIdanimal(int idanimal) {
        this.idanimal = idanimal;
    }

    public int getIdcategory() {
        return idcategory;
    }

    public void setIdcategory(int idcategory) {
        this.idcategory = idcategory;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    // end region
}
