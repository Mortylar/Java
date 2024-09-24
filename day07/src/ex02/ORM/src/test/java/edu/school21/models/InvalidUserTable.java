package edu.school21.models;

import edu.school21.annotations.OrmColumn;
import edu.school21.annotations.OrmColumnId;
import edu.school21.annotations.OrmEntity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@OrmEntity(table = "simple_invalid_user")
public class InvalidUserTable {

    @EqualsAndHashCode.Exclude @OrmColumnId private Long id;

    @EqualsAndHashCode.Exclude @OrmColumnId private Long secondId;

    @OrmColumn(name = "firstName", length = 10) private String firstName;

    @OrmColumn(name = "lastName", length = 10) private String lastName;

    @OrmColumn(name = "age") private Integer age;
}
