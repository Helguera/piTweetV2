/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer.Classes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author Javier Helguera <contact at javierhelguera.com>
 */
public class MyDate {

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yy HH:mm");
    private Calendar calendar;

    public MyDate() {
        calendar = Calendar.getInstance();
    }
    
    public MyDate(String date){
       calendar = Calendar.getInstance();
       int day = Integer.parseInt(date.substring(0, 2));
       int month = Integer.parseInt(date.substring(3, 5))-1;
       int year = Integer.parseInt(date.substring(6, 8));
       int hour = Integer.parseInt(date.substring(9, 11));
       int minute = Integer.parseInt(date.substring(12,14));
       //System.out.println(day +" "+ month +" "+ year +" "+ hour +" "+ minute);
       calendar.set(year, month, day, hour, minute);
    }
    
    public String toString(){
        return sdf.format(calendar.getTime());
    }
    
    public Calendar getCalendar(){
        return calendar;
    }
}
