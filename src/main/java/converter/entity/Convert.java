package converter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
/**
 * @author Панферов Владимир
 */
@Entity
@Table(name = "convert_history")
@Data
public class Convert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ch_id", nullable = false)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private Integer userId;

    @Column(name = "from_curr", nullable = false)
    private Integer fromCurr;

    @Column(name = "to_curr", nullable = false)
    private Integer toCurr;

    @NotNull
    @Min(0)
    @Column(name = "from_value", nullable = false)
    private Double fromValue;

    @Column(name = "to_value", nullable = false)
    private Double toValue;

    @Column(name = "c_date", nullable = false)
    private Date date;

    public Convert(){}

    public Convert(Integer id, Integer userId, Integer fromCurr, Integer toCurr, @NotNull @Min(0) Double fromValue, Double toValue, Date date) {
        this.id = id;
        this.userId = userId;
        this.fromCurr = fromCurr;
        this.toCurr = toCurr;
        this.fromValue = fromValue;
        this.toValue = toValue;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFromCurr() {
        return fromCurr;
    }

    public void setFromCurr(Integer fromCurr) {
        this.fromCurr = fromCurr;
    }

    public Integer getToCurr() {
        return toCurr;
    }

    public void setToCurr(Integer toCurr) {
        this.toCurr = toCurr;
    }

    public Double getFromValue() {
        return fromValue;
    }

    public void setFromValue(Double fromValue) {
        this.fromValue = fromValue;
    }

    public Double getToValue() {
        return toValue;
    }

    public void setToValue(Double toValue) {
        this.toValue = toValue;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
