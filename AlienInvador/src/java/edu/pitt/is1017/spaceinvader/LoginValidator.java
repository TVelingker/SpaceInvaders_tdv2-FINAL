package edu.pitt.is1017.spaceinvader;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import edu.pitt.is1017.spaceinvader.User;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Tanika
 */
@WebServlet(urlPatterns = {"/loginValidator"})
public class LoginValidator extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {

            String userName = "";
            String password = "";
            String message = "";
            User user;
            if (request.getParameter("btnSubmit") != null) {
                if (request.getParameter("txtUserName") != null) {
                    if (!request.getParameter("txtUserName").equals("")) {
                        userName = request.getParameter("txtUserName");

                    }
                }

                if (request.getParameter("txtPassword") != null) {
                    if (!request.getParameter("txtPassword").equals("")) {
                        password = request.getParameter("txtPassword");

                    }
                }
                if (userName.equals("") || password.equals("")) {
                    message = "You must enter both username and password";
                } else {
                    user = new User(userName, password);
                    message = message + user.getUserID();

                   if (user.getUserID() > 0) {
                       String playerName = user.getFirstName() + " " + user.getLastName();
                       response.sendRedirect("game.jsp?userID=" + user.getUserID());                     
                    }
                   else 
                        message = "Incorrect username or password. Please try again.";

                }
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Alien Invader - Login</title>");
                out.println("</head>");
                out.println("<body>");
                out.println("<script>alert('" + message + "');</script>");
                out.println("</body>");
                out.println("</html>");

            }
        }
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
