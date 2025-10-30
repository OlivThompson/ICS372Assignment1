package org.FoodHub;

import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;

public class DateFormatter {
    private String formattedUTCDate;
    public DateFormatter(Long dateNum){
        Instant instant = Instant.ofEpochMilli(dateNum);
        ZonedDateTime zonedTime = instant.atZone(ZoneOffset.UTC);

        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

        formattedUTCDate = zonedTime.format(formattedDate);
    }

    public String getDate(){
        return formattedUTCDate;
    }
}
