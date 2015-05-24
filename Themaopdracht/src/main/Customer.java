package main;

import java.time.LocalDate;
import java.util.ArrayList;

public class Customer {
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String address;
    private String country;
    private String phoneNumber;
    private String postal;
    private LocalDate dateOfBirth, lastVisit, lastMaintenance;
    private boolean isOnBlackList, notified;
    private final ArrayList<Car> cars;

    public Customer(String email, String password, String firstName, String lastName, LocalDate dateOfBirth, String postal, String address, String country, String phoneNumber) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.postal = postal;
        this.address = address;
        this.country = country;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        cars = new ArrayList<>();
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setIsOnBlackList(boolean isOnBlackList) {
        this.isOnBlackList = isOnBlackList;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    public void setLastVisit(LocalDate lastVisit) {
        this.lastVisit = lastVisit;
    }

    public void setLastMaintenance(LocalDate lastMaintenance) {
        this.lastMaintenance = lastMaintenance;
        this.lastVisit = lastMaintenance;

    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getCountry() {
        return country;
    }


    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public LocalDate getLastVisit() {
        return lastVisit;
    }

    public boolean isOnBlackList() {
        return isOnBlackList;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPostal() {
        return postal;
    }

    public boolean isNotified() {
        return notified;
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

    @Override
    public String toString() {
        return email;
    }
}
