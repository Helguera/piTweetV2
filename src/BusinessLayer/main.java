/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import BusinessLayer.Classes.MyDate;
import BusinessLayer.Classes.Tweet;
import DataAccessLayer.Connector;
import DataAccessLayer.TweetDB;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Javier Helguera
 */
public class main {
    
    // FILE LINE POSITIONS
        public static int text_line = 2;
        public static int publish_date_line = 3;
        public static int image1_line = 4;
        public static int image2_line = 5;
        public static int image3_line = 6;
        public static int image4_line = 7;
        
        
        
        public static String url = "/home/javier/NetBeansProjects/piTweetV2/files/tweets/";
    
    public static void main(String args[]) {
        
        
        // PROGRAM VARIABLES
        
        ArrayList<String> files = new ArrayList<String>();
        Connector connector = new Connector();

        // PROGRAM FUNCTIONS
        connector.connect();
        getFiles(files, url);     
        addFilesToDB(files);

    }

    public void readFile() {

    }

    public static void getFiles(ArrayList files, String url) {
        File directory = new File(url);
        String[] filesindir = directory.list();
        Arrays.sort(filesindir);

        for (int i = 0; i < filesindir.length; i++) {
            if (isTw(filesindir[i]) || isDm(filesindir[i])) {
                files.add(filesindir[i]);
            }
        }

    }

    public static boolean isTw(String file) {
        String extension;
        for (int i = file.length() - 1; i > -1; i--) {

            if (file.charAt(i) == '.') {
                if (file.substring(i + 1).equals("tw")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean isDm(String file) {
        String extension;
        for (int i = file.length() - 1; i > -1; i--) {

            if (file.charAt(i) == '.') {
                if (file.substring(i + 1).equals("dm")) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void addFilesToDB(ArrayList files) {
       
        File file;
        Tweet tweet;
        ArrayList<String> lines;
        TweetDB tweetDB = new TweetDB();
        for (int i = 0; i < files.size(); i++) {
             //TWEETS
             tweet = new Tweet();
             lines = new ArrayList<String>();
            if (isTw((String) files.get(i))) {
                file = new File(url+(String) files.get(i));
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                }catch(Exception e){
                    
                }
                
                tweet.setFileName((String) files.get(i));
                tweet.setText(getUsefulData(lines,text_line));
                tweet.setCreationDate(new MyDate());
                tweet.setPublishDate(new MyDate(getUsefulData(lines,publish_date_line)));
                tweet.setImage1(getUsefulData(lines,image1_line));
                tweet.setImage2(getUsefulData(lines,image2_line));
                tweet.setImage3(getUsefulData(lines,image3_line));
                tweet.setImage4(getUsefulData(lines,image4_line));
                
                tweetDB.saveTweet(tweet);
                
            }
            //DM
        }

        
    }
    
    public static String getUsefulData(ArrayList<String> lines, int line_number){
        String result = null;
        String line = lines.get(line_number);
        for (int i=0; i<line.length(); i++){
            if (line.charAt(i)=='='){
                result = line.substring(i+1);
            }
        }
        return result;
    }
}
