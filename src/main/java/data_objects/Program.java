package data_objects;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Program {

    private String external_id;
    private String object_id;
    private String year;
    private String title;
    private String description;
    private String schedule_string;
    private Genre genre;
    private Category category;
    private Pictures pictures;
    private String virtual_object_id;
    private String start;
    private String start_timestamp;
    private String end;
    private String end_timestamp;
    private Program[] grouped_programs;
    private String schedule_type;
    private LocalDateTime programStartTime;
    private LocalDateTime programEndTime;

    @Override
    public boolean equals(Object o) {
        return start.equals(((Program)o).getStart())
                && end.equals(((Program)o).getEnd())
                && title.equals(((Program)o).getTitle());
    }

    @Override
    public String toString() {
        return title;
    }
}
