/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pitt.is1017.spaceinvader;

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
@WebServlet(urlPatterns = {"/registerValidator"})
public class RegisterValidator extends HttpServlet {

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
            String firstName = "";
            String lastName = "";
            String email = "";
            String password = "";
            String confirmPassword = "";
            String message = "";
            User user;
            if (request.getParameter("btnSubmit") != null) {
                if (request.getParameter("txtFirstName") != null) {
                    if (!request.getParameter("txtFirstName").equals("")) {
                        firstName = request.getParameter("txtFirstName");

                    }
                }
                if (request.getParameter("txtLastName") != null) {
                    if (!request.getParameter("txtLastName").equals("")) {
                        lastName = request.getParameter("txtLastName");

                    }
                }
                if (request.getParameter("txtEmail") != null) {
                    if (!request.getParameter("txtEmail").equals("")) {
                        email = request.getParameter("txtEmail");

                    }
                }
                if (request.getParameter("txtPassword") != null) {
                    if (!request.getParameter("txtPassword").equals("")) {
                        password = request.getParameter("txtPassword");

                    }
                }
                if (request.getParameter("txtConfirmPassword") != null) {
                    if (!request.getParameter("txtConfirmPassword").equals("")) {
                        confirmPassword = request.getParameter("txtConfirmPassword");

                    }
                }
                if (!firstName.equals("") && !lastName.equals("") && !email.equals("")
                        && !password.equals("")
                        && !confirmPassword.equals("")) {
                    if (password.equals(confirmPassword)) {
                        user = new User(lastName, firstName, email, password);
                        if (user.getUserID() > 0) {
                            String playerName = user.getFirstName() + " " + user.getLastName();
                            response.sendRedirect("game.jsp?playerName=" + playerName);
                        } else {
                            message = "Error registering your id. Please call the helpline.";
                        }
                    } else {
                        message = "Passwords don't match";
                    }

                } else {
                    message = "You must enter First Name, Last Name, Email, Password, and Confirm Password";
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
