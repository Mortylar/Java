package edu.school21.sockets.models;

import edu.school21.sockets.models.Message;
import java.util.Calendar;
import java.util.List;
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
    private String name;
    private List<Message> messages;
}
