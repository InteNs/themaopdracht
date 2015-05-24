package login;

import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by InteNs on 13-5-2015.
 */
public class LogoutServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;


    protected void doPost( HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String loggedusers = req.getServletContext().getRealPath("/")+"loggedusers.txt";
        //removeLineFromFile(loggedusers, ((User)req.getSession().getAttribute("loggedInuser")).getEmail());
        ServletContext context = req.getServletContext();
        if(context.getAttribute("loggedusers") != null)
            ((ArrayList<User>) context.getAttribute("loggedusers")).remove(req.getSession().getAttribute("loggedInuser"));
        RequestDispatcher rd;
        rd = req.getRequestDispatcher("index.jsp");
        rd.forward(req,resp);
    }
    public void removeLineFromFile(String file, String lineToRemove) {
        try {
            File inFile = new File(file);

            if (!inFile.isFile()) {
                System.out.println("Parameter is not an existing file");
                return;
            }

            //Construct the new file that will later be renamed to the original filename.
            File tempFile = new File(inFile.getAbsolutePath() + ".tmp");

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter pw = new PrintWriter(new FileWriter(tempFile));

            String line = null;

            //Read from the original file and write to the new
            //unless content matches data to be removed.
            while ((line = br.readLine()) != null) {
                if (!line.trim().equals(lineToRemove)) {
                    line = line.replace("\n", "");
                    pw.println(line);
                    pw.flush();
                }
            }
            pw.close();
            br.close();

            //Delete the original file
            if (!inFile.delete()) {
                System.out.println("Could not delete file");
                return;
            }

            //Rename the new file to the filename the original file had.
            if (!tempFile.renameTo(inFile))
                System.out.println("Could not rename file");

        }
        catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

