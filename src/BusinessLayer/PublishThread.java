/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import java.io.File;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.UploadedMedia;

/**
 *
 * @author Javier Helguera <contact at javierhelguera.com>
 */
public class PublishThread implements Runnable {

    private Calendar now = Calendar.getInstance();
    private StatusUpdate status_update;
    private UploadedMedia media;
    private long[] mediaIds;
    private ArrayList<String> mediaIdsAL = new ArrayList<>();
    private File file;
    private ArrayList<Integer> published = new ArrayList<>();

    Twitter twitter = main.authorization.getTwitter();

    public void run() {
        System.out.println("Checking tweets...");
        System.out.println();

        while (true) {
            for (int i = 0; i < main.scheduled_tweets.size(); i++) {
                if (main.scheduled_tweets.get(i).getPublishDate().getCalendar().getTime().before(now.getTime())) {
                    status_update = new StatusUpdate(main.scheduled_tweets.get(i).getText());
                    if (main.scheduled_tweets.get(i).getImage1() != null) {
                        try {
                            media = twitter.uploadMedia(new File(main.scheduled_tweets.get(i).getImage1()));
                            mediaIdsAL.add(Long.toString(media.getMediaId()));
                        } catch (TwitterException ex) {
                            Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (main.scheduled_tweets.get(i).getImage2() != null) {
                        try {
                            media = twitter.uploadMedia(new File(main.scheduled_tweets.get(i).getImage2()));
                            mediaIdsAL.add(Long.toString(media.getMediaId()));
                        } catch (TwitterException ex) {
                            Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (main.scheduled_tweets.get(i).getImage3() != null) {
                        try {
                            media = twitter.uploadMedia(new File(main.scheduled_tweets.get(i).getImage3()));
                            mediaIdsAL.add(Long.toString(media.getMediaId()));
                        } catch (TwitterException ex) {
                            Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    if (main.scheduled_tweets.get(i).getImage4() != null) {
                        try {
                            media = twitter.uploadMedia(new File(main.scheduled_tweets.get(i).getImage4()));
                            mediaIdsAL.add(Long.toString(media.getMediaId()));
                        } catch (TwitterException ex) {
                            Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    try {
                        if (mediaIdsAL.size() > 0) {
                            mediaIds = new long[mediaIdsAL.size()];
                            for (int j = 0; j < mediaIds.length; j++) {
                                mediaIds[j] = Long.parseLong(mediaIdsAL.get(j));
                            }
                            mediaIdsAL.clear();
                            status_update.setMediaIds(mediaIds);
                        }
                        twitter.updateStatus(status_update);
                        System.out.println("  ✔ Tweet posted -> " + main.scheduled_tweets.get(i).getFileName() + " at " + main.scheduled_tweets.get(i).getPublishDate().toString());
                        file = new File(main.scheduled_tweets.get(i).getFileName());
                        //published.add(i);
                        file.renameTo(new File("./Posted/" + file.getName()));
                    } catch (TwitterException ex) {
                        Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    sleep(10000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
            /*for (Integer item : published) {
                int i = item;
                main.scheduled_tweets.remove(item);
            }*/
            main.scheduled_tweets.clear();
            published.clear();
            
            try {
                sleep(60000);
            } catch (InterruptedException ex) {
                Logger.getLogger(PublishThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
