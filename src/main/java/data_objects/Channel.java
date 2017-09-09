package data_objects;

import lombok.Getter;

/**
 * Created by alan on 06.09.17.
 */
@Getter
public class Channel {

    private String id;
    private String external_id;
    private String title;
    private Program[] programs;
    private Pictures pictures;
}
