package controllers;

import main.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
     * search for customer (on firstname, email and address)
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
                        customer.getRealName().contains(newVal)
                                || customer.getEmail().contains(newVal)
                                || customer.getAddress().contains(newVal))
                .forEach(results::add);
        return results;
    }

    public void changeCustomerInfo(Customer customer,String email, String password, String realName,LocalDate dateOfBirth, String address,String postal,String phoneNumber) {
        if (email !=null)       customer.setEmail(email);
        if (password !=null)    customer.setPassword(password);
        if (realName !=null)   customer.setRealName(realName);
        if (dateOfBirth !=customer.getDateOfBirth()) customer.setDateOfBirth(dateOfBirth);
        if (postal != null)     customer.setPostal(postal);
        if (address !=null)     customer.setAddress(address);
        if (phoneNumber != null)customer.setPhoneNumber(phoneNumber);
    }


    public void newCustomer(String email, String password, String realName,LocalDate dateOfBirth, String address, String postal, String phoneNumber ) {
        customers.add(new Customer(email, password, realName, dateOfBirth, postal, address, phoneNumber));
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