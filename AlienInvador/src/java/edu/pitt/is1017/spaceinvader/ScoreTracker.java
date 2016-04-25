/**
 * 
 */
package edu.pitt.is1017.spaceinvader;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Keeps track of scores. Saves scores in the database. 
 * 
 * @author Tanika Velingker
 * @version 1.0
 */


/**
 * Constructor for the class
 */


public class ScoreTracker {
   
    private User user;
    int currentScore;
    int highestScore;
    String gameID;
    private DbUtilities db = new DbUtilities();

    public ScoreTracker () {
    }
    
    public ScoreTracker (User user) {
     this.user = user;
     this.currentScore = 0;
     this.gameID = UUID.randomUUID().toString();
     this.highestScore = 0; 
    }
    
     public ScoreTracker (int userID, String gameID) {
     //            System.out.println("userName:" + userName + " " +  "gameID" + gameID);

     this.user = new User(userID);
     this.currentScore = 0;
     this.gameID = gameID;
     this.highestScore = 0;
    }
    
    void recordScore (int point) {
     this.currentScore = this.currentScore + point;
     String sql = "INSERT INTO runningscores ";
     sql = sql + "(gameID,scoreType,scoreValue,fk_userID,dateTimeEntered) ";
     sql = sql + " VALUES ";
     sql = sql + "('" + gameID + "',b'"
             + "1','"              
             + this.currentScore + "','"           
             + this.user.getUserID() + "',"
             + "NOW())";
     //System.out.println(currentScore);
     db.executeQuery(sql);
    }

    
    /**
     * Records final score in the database
     */

    void recordFinalScore () {
                      
        String sql = "INSERT INTO finalscores ";
        sql = sql + "(gameID,scoreValue,fk_userID,dateTimeEntered) ";
        sql = sql + " VALUES ";
        sql = sql + "('" + gameID + "','"
                + this.currentScore + "','"
                + this.user.getUserID() 
                + "', NOW())";
        //System.out.println(sql);
        db.executeQuery(sql); 
    }

    /**
     * @return the currentScore
     */
    public int getCurrentScore() {
        return currentScore;
    }

    
    
    /**
     * @param currentScore the currentScore to set
     */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }

    /**
     * @return the highestScore
     */
    public int getHighestScore() {
        return highestScore;
    }
    
        public User getUser() {
        return this.user;
    }
    
    /**
     * Retrieves the highest score for the user
     */

    private int retrieveHighestScore (int userID) {
        int highestScore = 0;

             String query = "SELECT MAX(scoreValue) as maxScore FROM finalscores  WHERE fk_userID="
                    + userID;
            ResultSet rs = db.getResultSet(query);

            try {
                while (rs.next()) {
                    highestScore = rs.getInt("maxScore");
                }

            } catch (SQLException e) {

            } finally {
                db.closeConnection();
            }
        
        return highestScore;
    }
    
    /**
     * Gets scores and prepares message for display 
     */

    
    public User getGameStats () {
        
        StringBuffer msg = new StringBuffer();
        
        msg.append("Your current score is  ");
        msg.append(this.currentScore + "\n");

//        this.highestScore = retrieveHighestScore (user.getUserID());

        msg.append("Your highest score is  ");
        msg.append(this.highestScore + "\n");

            User user = new User();
            String query = "SELECT lastName, firstName, MAX(scoreValue) as maxScore " + 
                           "FROM finalscores JOIN users ON fk_userID = userID GROUP BY " + 
                           "lastName, firstName order by 3 desc";
            ResultSet rs = db.getResultSet(query);

            try {
                if (rs.next()) {

                    user.setLastName(rs.getString("lastName"));
                    user.setFirstName(rs.getString("firstName"));                   
                    user.setHighestScore(rs.getInt("maxScore"));
                }
            } catch (SQLException e) {

            } finally {
                db.closeConnection();
            }
        
        return user;
    }
    
}

