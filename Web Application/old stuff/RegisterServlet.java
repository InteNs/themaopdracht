package session;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
public class RegisterServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException {
        RequestDispatcher rd;
		String email          = req.getParameter("emailreg");
		String password       = req.getParameter("passwordreg");
		String firstname      = req.getParameter("firstname");
		String lastName       = req.getParameter("lastname");
        LocalDate dateOfBirth = req.getParameter("birth");//TODO combobox maken
		String address        = req.getParameter("address");
        String postal         = req.getParameter("")
		String phoneNumber    =
		//System.out.println(email + password + firstname + surName + address + country);
		if(!email.equals("")
                && !password.equals("")
				&& !firstname.equals("")
				&& !surName.equals("")
				&& !address.equals("")
				&& !country.equals("")
                && req.getParameter("emailrep").equals(email)
                && req.getParameter("passwordrep").equals(password)){
            ServletContext context = req.getServletContext();
			User user = new User(null, email,password,firstname,surName,address,country);
            if(context.getAttribute("users") != null) ((ArrayList<User>) context.getAttribute("users")).add(user);
            else {
                context.setAttribute("users",new ArrayList<User>());
                ((ArrayList<User>)context.getAttribute("users")).add(user);
            }
            System.out.println(context.getAttribute("users"));
            //String file = req.getServletContext().getRealPath("/")+"users.txt";
            //BufferedWriter out = new BufferedWriter(new FileWriter(file,true));
			//out.write(email +"-"+ password +"-"+ firstname +"-"+ surName +"-"+ address +"-"+ country+ "\n");
            //out.close();
            req.setAttribute("succesReg", "<div class=\"alert alert-success\" role=\"alert\" style=\"margin-top:20px;\">Registratie gelukt.</div>");
            rd = req.getRequestDispatcher("index.jsp");
            rd.forward(req, resp);
		}
		else {
			req.setAttribute("succesReg","<div class=\"alert alert-danger\" role=\"alert\" style=\"margin-top:20px;\">Registratie mislukt.</div>");
            rd = req.getRequestDispatcher("Registration.jsp");
            rd.forward(req, resp);
		}
	}
}