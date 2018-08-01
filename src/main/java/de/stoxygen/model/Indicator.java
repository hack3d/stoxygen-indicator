package de.stoxygen.model;

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
    private String indicatorSymbol;

    public Indicator() {}

    public Integer getIndicatorsId() {
        return indicatorsId;
    }

    public String getIndicatorName() {
        return indicatorName;
    }

    public String getIndicatorSymbol() {
        return indicatorSymbol;
    }

}
