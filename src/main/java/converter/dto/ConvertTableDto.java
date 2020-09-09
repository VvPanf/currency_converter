package converter.dto;

import converter.entity.Convert;
import converter.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Класс для хранения информации о конвертации для отображения в таблице.
 * @author Панферов Владимир
 */
@Data
@NoArgsConstructor
public class ConvertTableDto {
    private String amountCurr;
    private String resultCurr;
    private Double amount;
    private Double result;
    private Date date;
    private User user;

    public ConvertTableDto(Convert convert) {
        this.amount = convert.getAmount();
        this.result = convert.getResult();
        this.amountCurr = convert.getAmountCurr().getChrCode();
        this.resultCurr = convert.getResultCurr().getChrCode();
        this.date = convert.getDate();
        this.user = convert.getUser();
    }
}
