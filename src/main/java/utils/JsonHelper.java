package utils;

import com.google.gson.Gson;
import data_objects.Program;
import data_objects.Schedule;

import java.util.Arrays;
import java.util.List;

public class JsonHelper {

    public static List<Program> getBroadcastProgramsFrom(String source) {
        Gson gson = new Gson();
        Schedule schedule = gson.fromJson(source, Schedule.class);
        return Arrays.asList(schedule.getData()[0].getPrograms());
    }
}
