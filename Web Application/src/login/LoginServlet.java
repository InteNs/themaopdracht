package login;

import model.User;

import java.io.*;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	protected void doPost( HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException {
		boolean succes =false;
        // request dispatcher zorgd dat de applicatie weer door kan na deze code
        RequestDispatcher rd;
		String email = req.getParameter("email");
        User user = null;
        //String file = req.getServletContext().getRealPath("/")+"users.txt";
		String password = req.getParameter("password");
//        try {
//            BufferedReader in = new BufferedReader(new FileReader(file));
//            String str;
//            while ((str = in.readLine()) != null) {
//
//                String[]ar = str.split("-");
//                if (email.equals(ar[0])&&password.equals(ar[1])){
//                    user = ar[3]+" "+ar[2];
//                    succes=true;
//                    break;
//                }
//            }
//
//            in.close();
//        } catch (IOException e) {
//            System.out.println("File Read Error");
//        }
        //check for alle users in "users" of email&pass overeenkomen
        for(User saveduser : (ArrayList<User>)req.getServletContext().getAttribute("users")){
            if (saveduser.getEmail().equals(email)&& saveduser.getPassword().equals(password)){
                succes = true;
                user = saveduser;
            }

        }
        if (succes) {
            //req.setAttribute("succes","<div class=\"alert alert-success\" role=\"alert\" style=\"margin-top:20px;\">Login succesvol.</div>");
            System.out.println("succes");
            //zet waar de app heen moet als deze servlet klaar is
            rd = req.getRequestDispatcher("welcome.jsp");
            System.out.println(user);
            //zet de ingelogde user in de sessie
            req.getSession().setAttribute("loggedInuser",user);
            //maak een cookie met de email van de ingelogde user
            Cookie c = new Cookie("cEmail", user.getEmail());
            c.setMaxAge(2000);
            //voeg cookie toe aan de response
            resp.addCookie(c);
            //String loggedusers = req.getServletContext().getRealPath("/")+"loggedusers.txt";
            //PrintWriter pw = new PrintWriter(new FileWriter(loggedusers));
            //pw.println(email);
            //pw.flush();
            //pw.close();
        } else {
            req.setAttribute("succes","<div class=\"alert alert-danger\" role=\"alert\" style=\"margin-top:20px;\">Verkeerd emailadres en/of wachtwoord.</div>");
            System.out.println("failure");
            rd = req.getRequestDispatcher("");
        }
        //kill servlet en ga naar w/e de requestdispatcher wijst
        rd.forward(req, resp);
	}
}
