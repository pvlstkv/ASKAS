package com.example.javaserver.general.config.time_converter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;

public class DateMapper {
    @OffsetDateTimeToMilliConverter
    public static long OffsetDateTimeToUnixMilli(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toInstant().toEpochMilli();
    }

    @MilliToOffsetDateTimeConverter
    public static OffsetDateTime UnixMilliToOffsetDateTime(Long unixMilli) {
        return unixMilli == null
                ? OffsetDateTime.ofInstant(Instant.ofEpochMilli(System.currentTimeMillis()), ZoneId.systemDefault()) :
                OffsetDateTime.ofInstant(Instant.ofEpochMilli(unixMilli), ZoneId.systemDefault());
    }
}