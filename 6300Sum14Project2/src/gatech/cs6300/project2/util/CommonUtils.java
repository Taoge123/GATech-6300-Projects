package gatech.cs6300.project2.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class CommonUtils {

    public final static String DATE_FORMAT = "MM-dd-yyyy";

    private CommonUtils() {
    }

    public static Date stringToDate(String dateString) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.parse(dateString);
    }

    public static String dateToString(Date date) {
        SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
        return df.format(date);
    }

    public static Date getToday() {
        return Calendar.getInstance().getTime();
    }

    public static Date getYesterday() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    public static int getDaysBetween(Date startDate, Date endDate) {

        long timeDiffInMs = startDate.getTime() - endDate.getTime();
        long timeDiffInDays = TimeUnit.DAYS.convert(timeDiffInMs, TimeUnit.MILLISECONDS);
        return (int) Math.max(Math.min(Integer.MAX_VALUE, timeDiffInDays), Integer.MIN_VALUE);
    }

    public static String formatCurrency(double value) {
        NumberFormat format = NumberFormat.getCurrencyInstance();
        return format.format(value);
    }

}
