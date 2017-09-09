package utils;

import data_objects.Program;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

public class DateFormatter {

    public static void formatDate(List<Program> programsToFormat) {
        programsToFormat.forEach(program -> {
            program.setStart(fromBroadcastToReferenceFormat(program.getStart()));
            program.setEnd(fromBroadcastToReferenceFormat(program.getEnd()));
        });

    }

    private static String fromBroadcastToReferenceFormat(String inputDate) {
        DateTimeFormatter dateTimeFormatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern("MMM d, uuuu h:mm:ss a")
                .toFormatter(Locale.US);

        return LocalDateTime.parse(inputDate, dateTimeFormatter)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
