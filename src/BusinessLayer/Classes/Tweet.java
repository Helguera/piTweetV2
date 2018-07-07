/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer.Classes;

import java.util.Date;

/**
 *
 * @author Javier Helguera <contact at javierhelguera.com>
 */
public class Tweet {
    
    private String file_name;
    private Date creation_date;
    private Date publish_date;
    private String text;
    private String image1 = null;
    private String image2 = null;
    private String image3 = null;
    private String image4 = null;
    
    public Tweet(){
        
    }
    
    public Tweet(String file_name, String text, Date publish_date){
        this.file_name = file_name;
        this.text = text;
        this.publish_date = publish_date;
    }
    
    public void setFileName(String file_name){
        this.file_name=file_name;
    }
    
    public void setText(String text){
        this.text=text;
    }
    
    public void setPublishDate(Date publish_date){
        this.publish_date=publish_date;
    }
    
    public void setImage1(String image1){
        this.image1 = image1;
    }
    
    public void setImage2(String image2){
        this.image2 = image2;
    }
    
    public void setImage3(String image3){
        this.image3 = image3;
    }
    
    public void setImage4(String image4){
        this.image4 = image4;
    }
    
    public String getFileName(){
        return file_name;
    }
    
    public String getText(){
        return text;
    }
    
    public Date getCreationDate(){
        return creation_date;
    } 
    
    public Date getPublishDate(){
        return publish_date;
    }
    
    public String getImage1(){
        return image1;
    }
    
    public String getImage2(){
        return image2;
    }
    
    public String getImage3(){
        return image3;
    }
    
    public String getImage4(){
        return image4;
    }
    
    
    
    
    
}
