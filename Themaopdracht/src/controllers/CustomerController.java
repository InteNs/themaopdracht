package controllers;

import com.sun.xml.internal.bind.v2.TODO;
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

    public ArrayList<Customer> getCustomers() {
        return customers;
    }
    public List<Customer> getExpiredMaintenanceList() {
       return customers.stream()
                .filter(customer -> customer.getLastMaintenance().isBefore(LocalDate.now().minusMonths(6)))
                .collect(Collectors.toList());
    }
    public List<Customer> getExpiredVisitList(){
       return customers.stream()
                .filter(customer -> customer.getLastVisit().isBefore(LocalDate.now().minusMonths(2)))
                .collect(Collectors.toList());
    }
    public List<Customer> getBlackListedList(){
        return customers.stream()
                .filter(customer -> customer.isOnBlackList())
                .collect(Collectors.toList());
    }
    public void changeCustomerInfo(Customer customer){
        //TODO  implement method via httprequest
    }
}
