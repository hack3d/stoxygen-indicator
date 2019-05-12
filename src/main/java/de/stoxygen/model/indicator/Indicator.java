package de.stoxygen.model.indicator;

import de.stoxygen.model.Auditable;

import javax.persistence.*;

@Entity
public class Indicator extends Auditable<String> {
    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer indicatorsId;

    @Column(nullable = false)
    private String indicatorName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private IndicatorSymbol indicatorSymbol;

    public Indicator() {}

    public Integer getIndicatorsId() {
        return indicatorsId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public IndicatorSymbol getIndicatorSymbol() {
        return indicatorSymbol;
    }

    public void setIndicatorName(String indicatorName) {
        this.indicatorName = indicatorName;
    }

    public void setIndicatorSymbol(IndicatorSymbol indicatorSymbol) {
        this.indicatorSymbol = indicatorSymbol;
    }

}
