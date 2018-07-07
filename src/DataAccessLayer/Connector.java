/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier Helguera <contact at javierhelguera.com>
 */
public class Connector {
    
    String url = "/home/javier/NetBeansProjects/piTweetV2/files/piTweet";    //Set here the default path, not this one 
    private static Connection connect;
    
    public Connector(){
        
    }
    
    
    public void connect() {
        try {
            File file = new File(url);
            if (file.exists()==false){
                System.out.println("There is not a piTweet.db file in the default path");
                System.out.println("Exiting...");
                System.out.println();
                System.exit(0);
            }else{
                connect = DriverManager.getConnection("jdbc:sqlite:" + url);
            }
        } catch (SQLException ex) {
            System.err.println("Unable to connect to the database\n" + ex.getMessage());
            System.exit(0);
        }
    }
    
    public void close() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(Connector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    public static Connection getConnection(){
        return connect;
    }
    
}
