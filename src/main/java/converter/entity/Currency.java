package converter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author Панферов Владимир
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Length(max=3)
    private String numCode;

    @Length(max=3)
    private String chrCode;

    @Length(max=255)
    private String currName;

    @Min(value = 0)
    private Integer nominal;
}
