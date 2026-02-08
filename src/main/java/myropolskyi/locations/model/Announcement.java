package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Announcement message for app updates or notifications
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Announcement {

    @JsonProperty("messageId")
    private String messageId;

    @JsonProperty("message")
    /*this is the map in order to make messages in different languages*/
    private Map<String, String> message;
}
