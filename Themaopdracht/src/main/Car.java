package main;

class Car {

    private String type;
    private String kenteken;

    public Car(String type, String kenteken) {
        this.type = type;
        this.kenteken = kenteken;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKenteken() {
        return kenteken;
    }

    public void setKenteken(String kenteken) {
        this.kenteken = kenteken;
    }

    @Override
    public String toString() {
        return "";
    }
}
