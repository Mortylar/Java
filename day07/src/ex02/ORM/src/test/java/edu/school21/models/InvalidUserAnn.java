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
public class InvalidUserAnn {

    @EqualsAndHashCode.Exclude @OrmColumnId private Long id;

    @OrmColumn(name = "firstName", length = 10) private String firstName;

    private String lastName;

    @OrmColumn(name = "age") private Integer age;
}
