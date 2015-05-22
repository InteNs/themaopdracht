package controllers;

import main.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by InteNs on 17.mei.2015.
 */
public class CustomerController {
    ArrayList<Customer> customers;

    /**
     * create a new controller(use only once)
     */
    public CustomerController() {
        customers = new ArrayList<>();
    }

    /**
     * @return an arraylist with all the customers
     */
    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    /**
     * @return a list with customers who's vehicles need a maintenance(6 months)
     */
    public List<Customer> getExpiredMaintenanceList() {
        return customers.stream()
                .filter(customer -> customer.getLastMaintenance().isBefore(LocalDate.now().minusMonths(6)))
                .collect(Collectors.toList());
    }

    /**
     * @return a list with customers who haven't payed a visit in 2+ months
     */
    public List<Customer> getExpiredVisitList() {
        return customers.stream()
                .filter(customer -> customer.getLastVisit().isBefore(LocalDate.now().minusMonths(2)))
                .collect(Collectors.toList());
    }

    /**
     * @return a list with customers currently on the blacklist
     */
    public List<Customer> getBlackListedList() {
        return customers.stream()
                .filter(Customer::isOnBlackList)
                .collect(Collectors.toList());
    }

    /**
     * search for customer (on firstname, email and address
     * @param oldVal    old value of changelistener
     * @param newVal    new value of changelistener
     * @return a list with the results of the search
     */
    //may need to change depending on format of listeners in html/javascript or whatever(no clue)
    public List<Customer> searchCustomer(String oldVal, String newVal) {
        ArrayList<Customer> results = new ArrayList<>();

        if (oldVal != null && (newVal.length() < oldVal.length())) {
            //actor has deleted a character, so reset the search
            results.clear();
        }
        //add an item if any item that exists contains any value that has been searched for
        customers.stream()
                .filter(customer ->
                        customer.getFirstName().contains(newVal)
                                || customer.getEmail().contains(newVal)
                                || customer.getAddress().contains(newVal))
                .forEach(results::add);
        return results;
    }

    /**
     * change customer info
     * @param customer      the customer in question
     * @param email         @nullable
     * @param password      @nullable
     * @param firstName     @nullable
     * @param lastName      @nullable
     * @param address       @nullable
     * @param country       @nullable
     * @param dateOfBirth   @nullable
     */
    public void changeCustomerInfo(Customer customer,String email, String password, String firstName, String lastName, String address, String country, LocalDate dateOfBirth) {
        if (email !=null)       customer.setEmail(email);
        if (password !=null)    customer.setPassword(password);
        if (firstName !=null)   customer.setFirstName(firstName);
        if (lastName !=null)    customer.setLastName(lastName);
        if (address !=null)     customer.setAddress(address);
        if (country !=null)     customer.setCountry(country);
        if (dateOfBirth !=customer.getDateOfBirth())
                                customer.setDateOfBirth(dateOfBirth);
    }

    /**
     * create a new customer
     *
     * @param email       emailadress
     * @param password    password
     * @param firstName   firstname
     * @param lastName    lastname
     * @param address     adress(street + house number)
     * @param country     country
     * @param dateOfBirth dateOfBirth
     */
    public void newCustomer(String email, String password, String firstName, String lastName, String address, String country, LocalDate dateOfBirth) {
        customers.add(new Customer(email, password, firstName, lastName, address, country, dateOfBirth));
    }

    /**
     * remove customer by email
     *
     * @param email from the customer to be removed
     */
    public void removeCustomer(String email) {
        customers.stream()
                .filter(customer -> customer.getEmail().equals(email))
                .forEach(customers::remove);
    }

    /**
     * remove customer by object
     *
     * @param customer to be removed
     */
    public void removeCustomer(Customer customer) {
        customers.remove(customer);
    }
}