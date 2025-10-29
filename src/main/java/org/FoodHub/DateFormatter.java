package org.FoodHub;

import java.time.format.DateTimeFormatter;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;

public class DateFormatter {
    public String Dateformatter(Long dateNum){
        Instant instant = Instant.ofEpochSecond(dateNum);
        ZonedDateTime zonedTime = instant.atZone(ZoneOffset.UTC);

        DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");

        String formattedUTCDate = zonedTime.format(formattedDate);
        return formattedUTCDate;
    }
}
