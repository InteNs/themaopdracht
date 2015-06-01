package nl.dense_code.atd.session;

import controllers.CustomerController;
import main.Customer;
import main.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by InteNs on 29.mei.2015.
 */
public class register extends HttpServlet {
    String email;
    String password;
    String realName;
    LocalDate dateOfBirth;
    String address;
    String postal;
    String phoneNumber;
    ServletContext servletContext;
    RequestDispatcher requestDispatcher;
    CustomerController controller;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        servletContext = req.getServletContext();
        readContext();
        email 	= req.getParameter("email");
        password = req.getParameter("password");
        realName = req.getParameter("realname");
        dateOfBirth = LocalDate.parse(req.getParameter("date"));
        address = req.getParameter("address");
        postal = req.getParameter("postal");
        phoneNumber = req.getParameter("phonenumber");
        if (!doesExist(email)
                && !Objects.equals(email,"")
                && !Objects.equals(password,"")
                && !Objects.equals(realName,"")
                && !Objects.equals(dateOfBirth,"")
                && !Objects.equals(address,"")
                && !Objects.equals(postal,"")
                && !Objects.equals(phoneNumber,"")
                && email.equals(req.getParameter("emailrepeat"))
                && password.equals((req.getParameter("passwordrepeat")))
                ){
            controller.newCustomer(email,password,realName,dateOfBirth,address,postal,phoneNumber);

            requestDispatcher = req.getRequestDispatcher("/index.jsp");
        }
        else requestDispatcher = req.getRequestDispatcher("/registration.jsp");
        requestDispatcher.forward(req,resp);
    }
    private void readContext(){
       if (servletContext.getAttribute("usercontroller")== null)servletContext.setAttribute("usercontroller",new CustomerController());
       else controller = (CustomerController)servletContext.getAttribute("usercontroller");
    }
    private boolean doesExist(String email){
        for(Customer customer : controller.getCustomers())
            if (customer.getEmail().equals(email))return true;
        return false;
    }
}
