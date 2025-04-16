import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class LoginServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Hardcoded credentials for demonstration
        String validUsername = "admin";
        String validPassword = "password123";

        // Check if the provided credentials are correct
        if (username.equals(validUsername) && password.equals(validPassword)) {
            // If valid, forward to welcome.jsp with the username
            request.setAttribute("username", username);
            RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
            dispatcher.forward(request, response);
        } else {
            // If invalid, redirect back to login.html
            response.sendRedirect("login.html");
        }
    }
}
