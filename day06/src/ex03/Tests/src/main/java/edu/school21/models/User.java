package edu.school21.models;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class User {

    @Setter(AccessLevel.PRIVATE) private final long id;
    private String login;
    private String password;
    boolean authenticationSuccessStatus;
}
