package main;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer extends User {
    private LocalDate lastVisit;
    private LocalDate lastMaintenance;
    private boolean isOnBlackList;
    private final ArrayList<Car> cars;

    public Customer(String email, String password, String realName, LocalDate dateOfBirth, String address, String postal, String phoneNumber) {
        super(email, password,realName, dateOfBirth, address, postal, phoneNumber);
        cars = new ArrayList<>();
    }

    public void setLastMaintenance(LocalDate lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
        this.lastVisit = lastMaintenance;
    }

    public LocalDate getLastVisit() {
        return lastVisit;
    }

    public boolean isOnBlackList() {
        return isOnBlackList;
    }

    public LocalDate getLastMaintenance() {
        return lastMaintenance;
    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void removeCar(Car car){
        cars.remove(car);
    }

    public void setLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
    }

    public void setIsOnBlackList(boolean isOnBlackList) {
        this.isOnBlackList = isOnBlackList;
    }
}
