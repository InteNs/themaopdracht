package nl.dense_code.atd.session;

import controllers.ProductController;
import main.Product;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class product extends HttpServlet {
    String naam;
    String aantal;
    String minAantal;
    String prijs;
    String inkoopPrijs;
    String leverancier;
    String adres;
    String postcode;
    String plaats;
    ServletContext servletContext;
    RequestDispatcher requestDispatcher;
    ProductController controller;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        servletContext = req.getServletContext();
        readContext();
        naam = req.getParameter("naam");
        aantal = req.getParameter("aantal");
        minAantal = req.getParameter("minAantal");
        prijs = req.getParameter("prijs");
        inkoopPrijs = req.getParameter("inkoopPrijs");
        leverancier = req.getParameter("leverancier");
        adres = req.getParameter("adres");
        postcode = req.getParameter("postcode");
        plaats = req.getParameter("plaats");
        if (!Objects.equals(naam,"")
                && !Objects.equals(aantal,"")
                && !Objects.equals(minAantal,"")
                && !Objects.equals(prijs,"")
                && !Objects.equals(inkoopPrijs,"")
                && !Objects.equals(leverancier,"")
                && !Objects.equals(adres,"")
                && !Objects.equals(postcode,"")
                && !Objects.equals(plaats,"")
                ){
            controller.newPart();
                    ///newProduct(aantal, minAantal, prijs, inkoopPrijs, leverancier, adres, postcode, plaats);
            controller.removeProduct();

            requestDispatcher = req.getRequestDispatcher("/voorraad.jsp");
        }
        else requestDispatcher = req.getRequestDispatcher("/voorraad.jsp");
        requestDispatcher.forward(req,resp);
    }
    private void readContext(){
        if (servletContext.getAttribute("productcontroller")== null)servletContext.setAttribute("productcontroller",new ProductController());
        else controller = (ProductController)servletContext.getAttribute("productcontroller");
    }
}
