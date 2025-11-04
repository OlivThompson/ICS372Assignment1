package org.FoodHub;

import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;

/**
 *  Formats a millisecond epoch time as a UTC date string.
 */
public class DateFormatter {
    private String formattedUTCDate;
     /**
     * Constructs DateFormatter for the given epoch milliseconds.
     * Converts the time to a formatted UTC date string.
     * @param dateNum the time in milliseconds since epoch, or null.
     */
    public DateFormatter(Long dateNum){
        if (dateNum == null){
            this.formattedUTCDate = "N/A";
            return;
        }

        Instant instant = Instant.ofEpochMilli(dateNum);
        ZonedDateTime zonedTime = instant.atZone(ZoneOffset.UTC);

        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        formattedUTCDate = zonedTime.format(formattedDate);
    }
    /**
     * Returns the formatted UTC date string.
     *
     * @return formatted date string or "N/A" if null.
     */
    public String getDate(){
        return formattedUTCDate;
    }
}
