package myropolskyi.locations.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String message;
}
