package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;

public class DateHelper {

    private static final String SYNCHRONIZER_DATE_TIME_PATTERN = "EEEE, MMMM d, uuuu h:mm:ss a XXX";
    private static final String BROADCAST_DATE_TIME_PATTERN = "MMM d, uuuu h:mm:ss a";
    private static final String REFERENCE_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static LocalDateTime parseDateTimeFromSynchronizer(String inputDate) {
        return parseDateTime(inputDate, SYNCHRONIZER_DATE_TIME_PATTERN);
    }

    public static LocalDateTime parseDateTimeFromReference(String inputDate) {
        return parseDateTime(inputDate, REFERENCE_DATE_TIME_PATTERN);
    }

    public static LocalDateTime parseDateTimeFromBroadcast(String inputDate) {
        return parseDateTime(inputDate, BROADCAST_DATE_TIME_PATTERN);
    }

    private static LocalDateTime parseDateTime(String inputDate, String pattern) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter(Locale.US);

        return LocalDateTime.parse(inputDate, dateTimeFormatter);
    }
}
