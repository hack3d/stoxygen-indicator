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

    public Bond getBonds() {
        return bonds;
    }

    public void addBond(Bond bond) {
        this.bonds = bond;
    }

    public void addIndicatorConfiguration(IndicatorConfiguration indicatorConfiguration) {
        this.indicatorConfiguration = indicatorConfiguration;
    }

    public void setIndicatorKey(String indicatorKey) {
        this.indicatorKey = indicatorKey;
    }

    public void setIndicatorValue(String indicatorValue) {
        this.indicatorValue = indicatorValue;
    }
}
