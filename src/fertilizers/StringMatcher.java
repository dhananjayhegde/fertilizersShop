/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author ProjectTeam
 */
public class StringMatcher {
    
    public static void main(String[] args){
        
        String name = "Some Name";
        
        if(StringMatcher.alphabetCheck(name)){
            System.out.println("Only alphabets entered");
        } else {
            System.out.println("Digits and special chars are not allowed");
        }
    }
    
    /**
     * Returns true if "name" contains only English alphabets and spaces
     * Else, returns false
     * 
     * @param name
     * @return 
     */
    public static boolean alphabetCheck(String name){
        Pattern alpha = Pattern.compile("[a-zA-Z ]+");
        
        if(!name.isEmpty()){
            Matcher m = alpha.matcher(name);
            return m.matches();
        }
        return false;
    }
}
