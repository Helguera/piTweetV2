/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import java.io.File;
import static java.lang.Thread.sleep;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;


/**
 *
 * @author Javier Helguera <contact at javierhelguera.com>
 */
public class PublishThread implements Runnable{
    
    private Calendar now = Calendar.getInstance();
    
    Twitter twitter = main.authorization.getTwitter();
    
    public void run(){
        while (true){
            System.out.println("Comprobando tweets");
            for (int i=0; i<main.scheduled_tweets.size(); i++){
                if(main.scheduled_tweets.get(i).getPublishDate().getCalendar().before(now)){
                    StatusUpdate prueba = new StatusUpdate(main.scheduled_tweets.get(i).getText());
                    if(main.scheduled_tweets.get(i).getImage1() != null){
                        prueba.setMedia(new File(main.scheduled_tweets.get(i).getImage1()));
                    }
                    if(main.scheduled_tweets.get(i).getImage2() != null){
                        prueba.setMedia(new File(main.scheduled_tweets.get(i).getImage2()));
                    }
                    if(main.scheduled_tweets.get(i).getImage3() != null){
                        prueba.setMedia(new File(main.scheduled_tweets.get(i).getImage3()));
                    }
                    if(main.scheduled_tweets.get(i).getImage4() != null){
                        prueba.setMedia(new File(main.scheduled_tweets.get(i).getImage4()));
                    }
                    try {
                        twitter.updateStatus(prueba);
                        main.scheduled_tweets.remove(i);
                    } catch (TwitterException ex) {
                        Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                   
            }   
            try {
                sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
    }
}
