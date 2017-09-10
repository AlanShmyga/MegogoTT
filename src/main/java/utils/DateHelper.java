package utils;

import data_objects.Program;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateHelper {

    private static final String SYNCHRONIZER_DATE_TIME_PATTERN = "EEEE, MMMM d, uuuu h:mm:ss a XXX";
    private static final String BROADCAST_DATE_TIME_PATTERN = "MMM d, uuuu h:mm:ss a";
    private static final String REFERENCE_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static void formatStartEndDate(Program program) {
        program.setStart(fromBroadcastToReferenceFormat(program.getStart()));
        program.setEnd(fromBroadcastToReferenceFormat(program.getEnd()));
    }

    public static String getCurrentDateFrom(String response) {
        Pattern pattern = Pattern.compile("\"time_local\":\"(.+)\",");
        Matcher matcher = pattern.matcher(response);
        String foundMatch = "";
        while(matcher.find()) {
            foundMatch = matcher.group(1);
        }

        return fromSynchronizerToReferenceFormat(foundMatch);
    }

    private static String fromSynchronizerToReferenceFormat(String inputDate) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(SYNCHRONIZER_DATE_TIME_PATTERN)
                .toFormatter(Locale.US);

        return LocalDateTime.parse(inputDate, dateTimeFormatter)
                .format(DateTimeFormatter.ofPattern(REFERENCE_DATE_TIME_PATTERN));
    }

    private static String fromBroadcastToReferenceFormat(String inputDate) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(BROADCAST_DATE_TIME_PATTERN)
                .toFormatter(Locale.US);

        return LocalDateTime.parse(inputDate, dateTimeFormatter)
                .format(DateTimeFormatter.ofPattern(REFERENCE_DATE_TIME_PATTERN));
    }
}
