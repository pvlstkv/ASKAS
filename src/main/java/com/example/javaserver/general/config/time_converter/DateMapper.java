package com.example.javaserver.general.config.time_converter;

import java.time.OffsetDateTime;

public class DateMapper {
    @OffsetDateTimeConverter
    public static long OffsetDateTimeToUnixMilli(OffsetDateTime offsetDateTime) {
        return offsetDateTime.toInstant().toEpochMilli();
    }
}