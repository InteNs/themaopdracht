package main;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer {
    private String name, place, email, address, postal, bankAccount, tel;
    private LocalDate dateOfBirth, lastVisit, lastMaintenance;
    private boolean isOnBlackList, notified;
    private final ArrayList<Car> cars = new ArrayList<>();

    public Customer(String name, String place, String bankAccount, LocalDate bornDate, String email, String postal, String tel, String adress, boolean isOnBlackList) {
        this.name = name;
        this.isOnBlackList = isOnBlackList;
        this.place = place;
        this.bankAccount = bankAccount;
        this.email = email;
        this.address = adress;
        this.postal = postal;
        this.tel = tel;
        this.dateOfBirth = bornDate;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public String getName() {
        return name;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPlace() {
        return place;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getPostal() {
        return postal;
    }

    public String getTel() {
        return tel;
    }

    public LocalDate getLastVisit() {
        return lastVisit;
    }

    public boolean isOnBlackList() {
        return isOnBlackList;
    }

    public void setOnBlackList(boolean isOnBlackList) {
        this.isOnBlackList = isOnBlackList;
    }

    public LocalDate getLastMaintenance() {
        return lastMaintenance;
    }

    public void setLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
    }

    public void setLastMaintenance(LocalDate lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
        this.lastVisit = lastMaintenance;

    }

    public ArrayList<Car> getCars() {
        return cars;
    }

    public void add(Car car) {
        cars.add(car);
    }

    public void removeCar(Car car){
        cars.remove(car);
    }

    @Override
    public String toString() {
        return name;
    }

}
