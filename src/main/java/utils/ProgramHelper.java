package utils;

import data_objects.Program;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ProgramHelper {

    static List<Program> filterListOfProgramsByDate(List<Program> programList, LocalDateTime currentDateTime) {
        return programList
                .stream()
                .filter(program -> program.getProgramEndTime().isAfter(currentDateTime)
                        && program.getProgramStartTime().isBefore(LocalDate.now().atTime(23, 59, 59)))
                .collect(Collectors.toList());
    }
}
