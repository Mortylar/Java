package edu.school21.sockets.models;

import edu.school21.sockets.models.User;
import java.util.Calendar;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    private Long id;
    private User sender;
    private String text;
    private Calendar time;

    @Override
    public String toString() {
        return String.format("%s - %s: %s", time.getTime(),
                             sender.getUserName(), text);
    }
}
