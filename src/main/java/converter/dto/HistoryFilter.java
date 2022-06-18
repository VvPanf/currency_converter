package converter.dto;

import converter.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Класс для хранение информации об истории конвертации.
 * @author Панферов Владимир
 */
@Data
@NoArgsConstructor
public class HistoryFilter {
    private User user;
    private Date date;
    private Long amountCurr;
    private Long resultCurr;
}
