/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import BusinessLayer.Classes.Authorization;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.TwitterException;

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
    public static ArrayList<Tweet> scheduled_tweets = new ArrayList<Tweet>();
    public static ArrayList<Tweet> scheduled_dm = new ArrayList<Tweet>();

    public static Authorization authorization;

    public static void main(String args[]) throws TwitterException, IOException {

        // PROGRAM VARIABLES
        ArrayList<String> files = new ArrayList<String>();
        PublishThread publishThread = null;

        // PROGRAM FUNCTIONS
        System.out.println("    ---------------- Welcome to piTweetV2 made by Helguera ----------------");
        System.out.println();

        readAuthFile();

        //while (true) {
            getFiles(files, url);
            addFilesToDB(files);
            if (publishThread == null) {
                publishThread = new PublishThread();
                publishThread.run();
            }
        //}

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
                file = new File(url + (String) files.get(i));
                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        lines.add(line);
                    }
                } catch (Exception e) {

                }

                tweet.setFileName((String) files.get(i));
                tweet.setText(getUsefulData(lines, text_line));
                tweet.setCreationDate(new MyDate());
                tweet.setPublishDate(new MyDate(getUsefulData(lines, publish_date_line)));
                tweet.setImage1(getUsefulData(lines, image1_line));
                tweet.setImage2(getUsefulData(lines, image2_line));
                tweet.setImage3(getUsefulData(lines, image3_line));
                tweet.setImage4(getUsefulData(lines, image4_line));

                scheduled_tweets.add(tweet);

            }
            //DM

        }

    }

    public static String getUsefulData(ArrayList<String> lines, int line_number) {
        String result = null;
        String line = lines.get(line_number);
        for (int i = 0; i < line.length(); i++) {
            if (line.charAt(i) == '=') {
                result = line.substring(i + 1);
                if (result.equals("")){
                    result = null;
                }
            }
        }
        return result;
    }

    public static void readAuthFile() {
        File file = new File(url + "auth_file.txt");
        if (file.exists()) {
            String token = new String();
            String tokenSecret = new String();
            FileReader fr;
            BufferedReader br;
            try {
                file = new File(url + "auth_file.txt");
                fr = new FileReader(file);
                br = new BufferedReader(fr);
                String line;
                int n = 1;
                while ((line = br.readLine()) != null) {
                    if (n == 1) {
                        //System.out.println(line);
                        token = line;
                    } else if (n == 2) {
                        //System.out.println(line);
                        tokenSecret = line;
                    }
                    n++;
                }
                authorization = new Authorization(token, tokenSecret);
                System.out.println("Tokens succesfully readed from auth_file.txt");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                authorization = new Authorization();
            } catch (IOException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TwitterException ex) {
                Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
