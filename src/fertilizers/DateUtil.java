/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fertilizers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ProjectTeam
 */
public class DateUtil {

    private static final long DAY_IN_MILLISECONDS = 86400000L; //one day in milliseconds

    /**
     * Returns 'date' as it is if number of days to add is 0<br>
     * Forwards date by as many days as given by 'days' if 'days' is positive<br>
     * Advances date by as many days as given by 'days' if 'days' is negative<br>
     *
     * @param date
     * @param days
     * @return
     */
    public static java.util.Date addDaysToDate(java.util.Date date, int days) {

        return new java.util.Date(date.getTime() + (days * DAY_IN_MILLISECONDS));

    }
    /**
     * returns true if "from" date is less than "to" date.
     * In all other cases, returns false
     * 
     * @param from
     * @param to
     * @return 
     */
    public static boolean compareDate(java.util.Date from, java.util.Date to){
        
        return DateUtil.dateDiff(from, to) >= 0;
    }
    
    /**
     * Returns the difference between two dates in number of milliseconds
     * 
     * @param from
     * @param to
     * @return 
     */
    public static long dateDiff(java.util.Date from, java.util.Date to){
        return (to.getTime() - from.getTime());
    }
    /**
     * Only if the difference between DAte Of Admission and Date of Birth is more
     * than 18 years and less than 25 years, returns true.
     * 
     * In all other cases, returns false
     * 
     * @param doa
     * @param dob
     * @return 
     */
    public static boolean isAllowedAgeForCollege(java.util.Date doa, java.util.Date dob){
        double days = 0;
        long years = 0;
        
        Double daysInMilSec = new Double(DateUtil.DAY_IN_MILLISECONDS);
        
        days = DateUtil.dateDiff(dob, doa);
        
        if(days > 0){
            years = Math.round(days / (365 * daysInMilSec)); //*approximation
            
            if(years >= 18 && years <= 25){
                return true;
            }
        }
        
        return false;
    }
    /**
     * Returns true if and only if "date" is greater than "today"
     * 
     * @param date
     * @return 
     */
    public static boolean isInFuture(java.util.Date date){
        java.util.Date today = new java.util.Date();        
        return ( DateUtil.dateDiff(today, date) > 0);
    }
    
    /**
     * Returns true if and only if "date" is less than "today"
     * 
     * @param date
     * @return 
     */
    public static boolean isInPast(java.util.Date date) {
        java.util.Date today = new java.util.Date();
        return (DateUtil.dateDiff(today, date) < 0);
    }
    
    /**
     * Validate if the given Date string is in the format dd/mm/yyyy
     * and if it can be converted to java.util.Date in dd/mm/yyyy format
     * 
     */
    private boolean validateSimpleDate(String dateText) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false);
        Date date;
        try {
            date = formatter.parse(dateText);
        } catch (ParseException pe) {
            return false;
        }

        return true;
    }
}
