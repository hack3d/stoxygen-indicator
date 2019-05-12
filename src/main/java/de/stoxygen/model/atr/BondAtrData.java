package de.stoxygen.model.atr;

import java.util.Date;

public class BondAtrData {

    private String bondIsin;
    private Float atrDataPoint;
    private String aggregate;
    private Date timestamp;

    public BondAtrData() {}

    public BondAtrData(String bondIsin, String aggregate, Float atrDataPoint, Date timestamp) {
        this.bondIsin = bondIsin;
        this.aggregate = aggregate;
        this.atrDataPoint = atrDataPoint;
        this.timestamp = timestamp;
    }

    public String getBondIsin() {
        return bondIsin;
    }

    public Float getAtrDataPoint() {
        return atrDataPoint;
    }

    public String getAggregate() {
        return aggregate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "BondAtrData{" +
                "bondIsin='" + bondIsin + '\'' +
                ", atrDataPoint=" + atrDataPoint +
                ", aggregate='" + aggregate + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
