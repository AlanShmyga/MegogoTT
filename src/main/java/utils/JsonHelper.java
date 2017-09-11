package utils;

import com.google.gson.Gson;
import data_objects.Channel;
import data_objects.Program;
import data_objects.Schedule;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static utils.DateHelper.parseDateTimeFromBroadcast;
import static utils.ProgramHelper.filterListOfProgramsByDate;

public class JsonHelper {

    public static List<Program> getBroadcastProgramsFrom(String source,
                                                         String channelName,
                                                         LocalDateTime currentDateTime) {
        Gson gson = new Gson();
        Schedule schedule = gson.fromJson(source, Schedule.class);

        List<Program> rawListOfPrograms =
                Arrays.asList(getChannelByChannelName(schedule.getData(), channelName).getPrograms());

        rawListOfPrograms.forEach(program -> {
            program.setProgramStartTime(parseDateTimeFromBroadcast(program.getStart()));
            program.setProgramEndTime(parseDateTimeFromBroadcast(program.getEnd()));
        });

        return filterListOfProgramsByDate(rawListOfPrograms, currentDateTime);
    }

    public static String getCurrentDateTimeFromSynchronizer(String response) {
        Pattern pattern = Pattern.compile("\"time_local\":\"(.+)\",");
        Matcher matcher = pattern.matcher(response);
        String foundMatch = "";
        while (matcher.find()) {
            foundMatch = matcher.group(1);
        }

        return foundMatch;
    }

    private static Channel getChannelByChannelName(Channel[] channels, String channelName) {
        for (Channel channel : channels) {
            if (channel.getTitle().equalsIgnoreCase(channelName)) {
                return channel;
            }
        }

        throw new IllegalArgumentException("Channel with specified name is not found in broadcast response");
    }
}
