package ch.open.chef.kitchen.api.v1;

import static lombok.AccessLevel.PRIVATE;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data @FieldDefaults(level = PRIVATE)
@XmlRootElement
@Builder @AllArgsConstructor @NoArgsConstructor
public class MenuDto {

    Long id;
    String name;
    String description;
    List<String> ingredientIds;

}
