package converter.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
/**
 * @author Панферов Владимир
 */
@Entity
@Data
public class Convert {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "amountCurr_id", nullable = false)
    private Currency amountCurr;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "resultCurr_id", nullable = false)
    private Currency resultCurr;

    @NotNull
    @Min(value = 0)
    private Double amount;

    @NotNull
    private Double result;

    @NotNull
    private Date date;
}
