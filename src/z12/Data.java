package z12;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.time.format.DateTimeFormatter;

import java.util.Date;

public class Data {
    private static String data;


private static final DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    public static String getDate()
    {
    Date date = new Date();


        data=(sdf.format(date));
        return data;
    }
}
