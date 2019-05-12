package de.stoxygen.model.form;

import javax.validation.constraints.NotNull;

public class AddIndicatorBondConfigurationForm {
    @NotNull
    private int indicatorsId;

    private Integer macdFast;

    private Integer macdSlow;

    private Integer bbAvg;

    private Integer atrLength;

    private Integer adxLength;

    private Integer rsiLength;


    public AddIndicatorBondConfigurationForm() {  }

    public int getIndicatorsId() {
        return indicatorsId;
    }

    public Integer getMacdFast() {
        return macdFast;
    }

    public Integer getMacdSlow() {
        return macdSlow;
    }

    public Integer getBbAvg() {
        return bbAvg;
    }

    public void setIndicatorsId(int indicatorsId) {
        this.indicatorsId = indicatorsId;
    }

    public void setMacdFast(Integer macdFast) {
        this.macdFast = macdFast;
    }

    public void setMacdSlow(Integer macdSlow) {
        this.macdSlow = macdSlow;
    }

    public void setBbAvg(Integer bbAvg) {
        this.bbAvg = bbAvg;
    }

    public Integer getAtrLength() {
        return atrLength;
    }

    public void setAtrLength(Integer atrLength) {
        this.atrLength = atrLength;
    }

    public Integer getAdxLength() {
        return adxLength;
    }

    public void setAdxLength(Integer adxLength) {
        this.adxLength = adxLength;
    }

    public Integer getRsiLength() {
        return rsiLength;
    }

    public void setRsiLength(Integer rsiLength) {
        this.rsiLength = rsiLength;
    }

    @Override
    public String toString() {
        return "AddIndicatorBondConfigurationForm{" +
                "indicatorsId=" + indicatorsId +
                ", macdFast=" + macdFast +
                ", macdSlow=" + macdSlow +
                ", bbAvg=" + bbAvg +
                ", atrLength=" + atrLength +
                ", adxLength=" + adxLength +
                ", rsiLength=" + rsiLength +
                '}';
    }
}
