package main;

public class ProductSupplier {
    private String name, adress, postal, place;

    public ProductSupplier(String name, String adress, String postal, String place) {
        this.name = name;

        this.adress = adress;
        this.postal = postal;
        this.place = place;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return name;
    }
}
