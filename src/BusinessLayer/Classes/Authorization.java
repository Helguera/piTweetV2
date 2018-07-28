package BusinessLayer.Classes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

public class Authorization {
    
    
    ConfigurationBuilder configBuilder = new ConfigurationBuilder();
    Twitter OAuthTwitter;

    private String oAuthConsumerKey = "";
    private String oAuthConsumerSecret = "";  //DONT SHARE THIS KEY WITH ANYONE
    private String oAuthAccessToken = "";
    private String oAuthAccessTokenSecret = "";

    public Authorization() throws IOException, TwitterException { //Constructor de la clase
        configBuilder.setDebugEnabled(true)
                .setOAuthConsumerKey(oAuthConsumerKey)
                .setOAuthConsumerSecret(oAuthConsumerSecret); 
        OAuthTwitter = new TwitterFactory(configBuilder.build()).getInstance();
        RequestToken requestToken = null;
        AccessToken accessToken = null;
        String url = null;
        do {
            try {
                requestToken = OAuthTwitter.getOAuthRequestToken();
                System.out.println("Request Tokens obtenidos con éxito.");
                System.out.println("Request Token: " + requestToken.getToken());
                System.out.println("Request Token secret: " + requestToken.getTokenSecret());
                url = requestToken.getAuthorizationURL();
                System.out.println("URL:" + requestToken.getAuthorizationURL());
            } catch (TwitterException ex) {
            }
            BufferedReader lectorTeclado = new BufferedReader(new InputStreamReader(System.in));
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec("firefox " + url);
            } catch (Exception e) {
                System.out.println("The default browser could not be opened. Paste the above URL on a browser and continue");
            }
            System.out.print("Write the digits you see in the browser and press enter ---> PIN: ");
            String pin = lectorTeclado.readLine();
            if (pin.length() > 0) {
                accessToken = OAuthTwitter.getOAuthAccessToken(requestToken, pin);
            } else {
                accessToken = OAuthTwitter.getOAuthAccessToken(requestToken);
            }
        } while (accessToken == null);
        System.out.println("Success obtaining tokens.");
        System.out.println("Access Token: " + accessToken.getToken());
        System.out.println("Access Token secret: " + accessToken.getTokenSecret());
        System.out.println("Those tokens are stored in auth_file.txt to use the next time you open the program");
        FileOutputStream fileOS = null;
        File file;
        String content = accessToken.getToken() + "\n" + accessToken.getTokenSecret();
        try {
            file = new File("./auth_file.txt");
            fileOS = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            byte[] contentInBytes = content.getBytes();
            fileOS.write(contentInBytes);
            fileOS.flush();
            fileOS.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fileOS != null) {
                    fileOS.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Authorization(String oAuthAccessToken, String oAuthAccessTokenSecret) {
        configBuilder.setOAuthConsumerKey(oAuthConsumerKey);
        configBuilder.setOAuthConsumerSecret(oAuthConsumerSecret);
        configBuilder.setOAuthAccessToken(oAuthAccessToken);
        configBuilder.setOAuthAccessTokenSecret(oAuthAccessTokenSecret);
        
        OAuthTwitter = new TwitterFactory(configBuilder.build()).getInstance();
    }
    
    public Twitter getTwitter(){
        return OAuthTwitter;
    }
}
