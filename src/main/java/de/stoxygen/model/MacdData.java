package de.stoxygen.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class MacdData extends Auditable<String> {

    @Id
    @Column(nullable = false)
    private Date time;

    @Column(nullable = false)
    private Integer exchangeId;

    @Column(nullable = false)
    private Float macdSignal;

    @Column(nullable = false)
    private Float fastMacd;

    @Column(nullable = false)
    private Float slowMacd;

    @ManyToOne
    private Bond bonds;

    public MacdData() {}

    public Date getTime() {
        return time;
    }

    public Integer getExchangeId() {
        return exchangeId;
    }

    public Float getMacdSignal() {
        return macdSignal;
    }

    public Float getFastMacd() {
        return fastMacd;
    }

    public Float getSlowMacd() {
        return slowMacd;
    }
}
