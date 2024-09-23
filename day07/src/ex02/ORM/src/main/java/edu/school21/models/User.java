package edu.school21.models;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
@OrmEntity(table = "simple_user")
public class User {

    @OrmColumnId private Long id;

    @OrmColumn(name = "firstName", length = 10) private String firstName;

    @OrmColumn(name = "lastName", length = 10) private String lastName;

    @OrmColumn(name = "age") private Integer age;
}
