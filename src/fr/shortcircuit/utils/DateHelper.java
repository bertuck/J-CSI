package fr.shortcircuit.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper
{
    public static DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");


    public static Date getDate(String strDate)
    {
        try	{return (Date) formatter.parse(strDate);}
        catch (Exception e) {System.out.println("DateHelper: getDate: " + e.toString()); return null;}
    }

    public static String getStringDate(Date date)
    {
        try	{return formatter.format(date);}
        catch (Exception e) {System.out.println("DateHelper: getStringDate: " + e.toString()); return "";}
    }

}
