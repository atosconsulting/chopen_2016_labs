package ch.open.chef.kitchen.api.v1;

import static lombok.AccessLevel.PRIVATE;

import ch.open.chef.kitchen.menu.MeasurementUnit;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data @FieldDefaults(level = PRIVATE)
@Builder @AllArgsConstructor @NoArgsConstructor
public class IngredientDto {

    BigDecimal measure;
    MeasurementUnit unit;
    String name;

}
