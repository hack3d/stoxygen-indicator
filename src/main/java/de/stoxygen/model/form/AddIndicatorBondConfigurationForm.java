package de.stoxygen.model.form;

import javax.validation.constraints.NotNull;

public class AddIndicatorBondConfigurationForm {
    @NotNull
    private int indicatorsId;

    private Integer macdFast;

    private Integer macdSlow;

    private Integer bbAvg;

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
}
