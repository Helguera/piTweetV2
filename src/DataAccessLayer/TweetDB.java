/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataAccessLayer;

import BusinessLayer.Classes.Tweet;
import java.sql.Connection;
import DataAccessLayer.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Javier Helguera <contact at javierhelguera.com>
 */
public class TweetDB {
    Connection connect;
    
    public TweetDB(){
        connect = Connector.getConnection();
    }
    
    public void saveTweet(Tweet tweet){
        try {
            PreparedStatement st = connect.prepareStatement("insert into Tweets (file_name, creation_date, publish_date, text, image1, image2, image3, image4) values (?,?,?,?,?,?,?,?)");
            st.setString(1, tweet.getFileName());
            st.setString(2, tweet.getCreationDate().toString());
            st.setString(3, tweet.getPublishDate().toString());
            st.setString(4, tweet.getText());
            st.setString(5, tweet.getImage1());
            st.setString(6, tweet.getImage2());
            st.setString(7, tweet.getImage3());
            st.setString(8, tweet.getImage4());
            st.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(TweetDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void getTweet(String file_name){
        
    }
    
}
