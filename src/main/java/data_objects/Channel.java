package data_objects;

import lombok.Getter;

@Getter
public class Channel {

    private String id;
    private String external_id;
    private String title;
    private Program[] programs;
    private Pictures pictures;
}
