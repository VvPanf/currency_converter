package converter.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

/**
 * @author Панферов Владимир
 */

@Entity
@Table(name = "Rate")
@Data
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rate_id", nullable = false)
    private Integer id;

    //@ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "curr_id", nullable = false)
    private Integer currId;

    @Column(name = "curr_value", nullable = false)
    private Double value;

    @Column(name = "rate_date", nullable = false)
    private Date date;

    public Rate() {
    }

    public Rate(Integer id, Integer currId, Double value, Date date) {
        this.id = id;
        this.currId = currId;
        this.value = value;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurrId() {
        return currId;
    }

    public void setCurrId(Integer currId) {
        this.currId = currId;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
