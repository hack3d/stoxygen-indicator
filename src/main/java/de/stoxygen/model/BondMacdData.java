package de.stoxygen.model;

import java.util.Date;

public class BondMacdData {

    private String bondIsin;
    private Float macdDataPoint;
    private String aggregate;
    private Float macdSignal;
    private Date timestamp;

    public BondMacdData() {}

    public BondMacdData(String bondIsin, String aggregate, Float macdDataPoint, Float macdSignal, Date timestamp) {
        this.bondIsin = bondIsin;
        this.aggregate = aggregate;
        this.macdDataPoint = macdDataPoint;
        this.macdSignal = macdSignal;
        this.timestamp = timestamp;
    }

    public String getBondIsin() {
        return bondIsin;
    }

    public Float getMacdDataPoint() {
        return macdDataPoint;
    }

    public Float getMacdSignal() {
        return macdSignal;
    }

    public String getAggregate() {
        return aggregate;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
