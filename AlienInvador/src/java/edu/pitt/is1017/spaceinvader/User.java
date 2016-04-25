/**
 * 
 */
package edu.pitt.is1017.spaceinvader;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides methods for login, register, retrieval, and update of user info 
 * 
 * @author Tanika Velingker
 * @version 1.0
 */


public class User {

    private int userID = -99999;
    private String lastName;
    private String firstName;
    private String email;
    private String password;
    private boolean loggedIn = false;
    int highestScore = 0;
    private DbUtilities db = new DbUtilities();

   
    /**
     * Logs the user into the Space Invader game. Checks the input credentials against the database
     * @param email
     * @param password
     */
    
    public User () {
        
    }
    
    public User(String email, String password) {
        String query = "SELECT userID, firstName, lastName, email, password FROM users where upper(email)='"
                + email.toUpperCase() + "' and password='" + password + "'";
        System.out.println(query);
        ResultSet rs = db.getResultSet(query);

        try {
            while (rs.next()) {
                this.userID = rs.getInt("userID");
                this.firstName = rs.getString("firstName");
                this.lastName = rs.getString("lastName");
                this.email = rs.getString("email");
                this.password = rs.getString("password");
                this.loggedIn = true;
            }

        } catch (SQLException e) {

        } finally {
            db.closeConnection();
        }
    }


    /**
     * Retrieves user info for the given UserID from the database
     * @param userID
     */
    public User(int userID) {
        String query = "SELECT userID, firstName, lastName, email, password FROM users WHERE userID="
                + userID;
        ResultSet rs = db.getResultSet(query);

        try {
            while (rs.next()) {
                this.userID = rs.getInt("userID");
                this.firstName = rs.getString("firstName");
                this.lastName = rs.getString("lastName");
                this.email = rs.getString("email");
                this.password = rs.getString("password");
            }

        } catch (SQLException e) {

        } finally {
            db.closeConnection();
        }

    }

    /**
     * Creates a user in the database based on the input from the Registration screen
     * @author Tanika Velingker
     */
    
    public User(String lastName, String firstName, String email, String password) {
        
        // Verify if the email is already registered in the database
        boolean emailExists = false;
        String query = "SELECT userID, firstName, lastName, email, password " +""
                + "FROM users WHERE upper(email)='"
                + email.toUpperCase() + "'";
        ResultSet rs = db.getResultSet(query);

        try {
            while (rs.next()) {
                emailExists = true;
            }

        } catch (SQLException e) {

        } finally {
            db.closeConnection();
        }
        // The email is not registered. Ok to register the new user.
        if (!emailExists) {
            String sql = "INSERT INTO users ";
            sql = sql + "(lastName,firstName, email, password) ";
            sql = sql + " VALUES ";
            sql = sql + "('" + lastName.toUpperCase().trim() + "','"
                    + firstName.toUpperCase().trim() + "','"
                    + email.toUpperCase().trim() + "','" + password + "')";
            //System.out.println(sql);
            db.executeQuery(sql);
            rs = db.getResultSet(query);
            try {
                while (rs.next()) {
                    this.userID = rs.getInt("userID");
                    this.firstName = rs.getString("firstName");
                    this.lastName = rs.getString("lastName");
                    this.email = rs.getString("email");
                    this.password = rs.getString("password");
                }

            } catch (SQLException e) {

            } finally {
                db.closeConnection();
            }
        }
    }

    /**
     * Saves user info currently stored in the class to the database.
     */
    
    public void saveUserInfo() {
        String sql = "UPDATE users set lastName='"
                + this.lastName.toUpperCase().trim() + "',firstName='"
                + this.firstName.toUpperCase().trim() + "',email='"
                + this.email.toUpperCase().trim() + "',password='" + password
                + "'" + " WHERE userid =" + this.userID;
        //System.out.println(sql);
        db.executeQuery(sql);
    }
    
    // Getters and Setters for the User class
    /**
     * @return the lastName of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName of the user to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the firstName of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName of the user to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the email of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email of the user to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password of the user to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the loggedIn flag
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }

    /**
     * @param loggedIn the loggedIn flag to set
     */
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    /**
     * @return the userID of the user
     */
    public int getUserID() {
        return userID;
    }
      
    /**
     * @return the firstName of the user
     */
    public int getHighestScore() {
        return highestScore;
    }

    /**
     * @param highestScore of the user to set
     */
    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }
    
}

