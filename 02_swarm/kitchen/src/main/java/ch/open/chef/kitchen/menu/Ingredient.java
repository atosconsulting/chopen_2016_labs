package ch.open.chef.kitchen.menu;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Enumerated;

import java.math.BigDecimal;

import static javax.persistence.EnumType.STRING;

@Embeddable @Getter @Setter
public class Ingredient {

    private BigDecimal measure;
    @Enumerated(STRING)
    private MeasurementUnit unit;
    private String name;
    private String stockId;

}
