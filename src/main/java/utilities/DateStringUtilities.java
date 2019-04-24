package utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringUtilities {

    /**
     * The application knows two date formats, one it uses as input,
     * the other it uses to display dates in the homepage table.
     * @param date, dateformat (yyyy-MM-dd) as a String
     * @return date, dateformat (DD MMM yyyy) as a String
     */
    public static String getParsedDate(String date){
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        inputFormat.setLenient(false);
        DateFormat outputFormat = new SimpleDateFormat("dd MMM yyyy");
        outputFormat.setLenient(false);
        Date result;
        try{
            result =  inputFormat.parse(date);
        } catch (ParseException ex){
            throw new Error("Unable to parse date string: " + date);
        }
        return outputFormat.format(result);
    }
}
