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
@OrmEntity(table = "simple_product")
public class Product {

    @EqualsAndHashCode.Exclude @OrmColumnId private Long id;

    @OrmColumn(name = "productName", length = 10) private String productName;

    @OrmColumn(name = "price") private Double price;

    @OrmColumn(name = "count") private Long count;

    @OrmColumn(name = "isAvailable") private Boolean isAvailable;
}
