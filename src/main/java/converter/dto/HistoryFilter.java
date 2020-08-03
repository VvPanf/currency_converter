package converter.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

/**
 * Класс для хранение информации и фильтрах для истории конвертации.
 * @author Панферов Владимир
 */
@Data
@NoArgsConstructor
public class HistoryFilter {
    private Date historyDate;
    private Integer historyFromCurr;
    private Integer historyToCurr;

    public Date getHistoryDate() {
        return historyDate;
    }

    public void setHistoryDate(Date historyDate) {
        this.historyDate = historyDate;
    }

    public Integer getHistoryFromCurr() {
        return historyFromCurr;
    }

    public void setHistoryFromCurr(Integer historyFromCurr) {
        this.historyFromCurr = historyFromCurr;
    }

    public Integer getHistoryToCurr() {
        return historyToCurr;
    }

    public void setHistoryToCurr(Integer historyToCurr) {
        this.historyToCurr = historyToCurr;
    }
}
