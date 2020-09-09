package converter.dto;

import converter.entity.Convert;
import converter.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Date;

/**
 * Класс для хранения информации о конвертации.
 * @author Панферов Владимир
 */
@Data
@NoArgsConstructor
public class ConvertHistoryDto {
    private Long amountCurrId;
    private Long resultCurrId;
    private Double amount;
    @NotNull
    @Min(value = 0)
    private Double result;
    private Date date;
    private User user;

    public ConvertHistoryDto(Convert convert) {
        this.amount = convert.getAmount();
        this.result = convert.getResult();
        this.amountCurrId = convert.getAmountCurr().getId();
        this.resultCurrId = convert.getResultCurr().getId();
        this.date = convert.getDate();
        this.user = convert.getUser();
    }
}
