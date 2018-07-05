/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BusinessLayer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Javier Helguera
 */
public class main {

    public static void main(String args[]) {
        
        // PROGRAM VARIABLES
        
        String url = "/home/javier/Desktop/tweets";
        ArrayList<String> files = new ArrayList<String>();
        
        // PROGRAM FUNCTIONS

        getFiles(files, url);
        
        for (int i=0; i<files.size(); i++){
            System.out.println(files.get(i));
        }
        
        
        
        

    }

    public void readFile() {

    }

    public static void getFiles(ArrayList files, String url){
        File directory = new File(url);
        String[] filesindir = directory.list();
        Arrays.sort(filesindir);
        
        for (int i=0; i<filesindir.length; i++){
            if (isTw(filesindir[i])){
                files.add(filesindir[i]);
            }
        }
        
    }
    
    public static boolean isTw (String file){
        String extension;
        for (int i=file.length()-1; i>-1; i--){
            
            if (file.charAt(i)=='.'){
                if (file.substring(i+1).equals("tw")){
                    return true;
                }
            }
        }
        
        return false;
    }
}
