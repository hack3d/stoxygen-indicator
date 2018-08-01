package de.stoxygen.model;

import javax.persistence.*;

@Entity
public class IndicatorBondSetting extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer indicatorBondSettingsId;

    @Column(nullable = false)
    private String indicatorKey;

    @Column(nullable = false)
    private String indicatorValue;

    @ManyToOne
    private Bond bonds;

    @ManyToOne
    private IndicatorConfiguration indicatorConfiguration;

    public IndicatorBondSetting() {}

    public Integer getIndicatorBondSettingsId() {
        return indicatorBondSettingsId;
    }

    public String getIndicatorKey() {
        return indicatorKey;
    }

    public String getIndicatorValue() {
        return indicatorValue;
    }

    public IndicatorConfiguration getIndicatorConfiguration() {
        return indicatorConfiguration;
    }
}
