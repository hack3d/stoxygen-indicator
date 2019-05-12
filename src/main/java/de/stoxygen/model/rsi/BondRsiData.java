package de.stoxygen.model.rsi;

import java.util.Date;

public class BondRsiData {

    private String bondIsin;
    private Float rsiDataPoint;
    private String aggregate;
    private Date timestamp;

    public BondRsiData() {}

    public BondRsiData(String bondIsin, String aggregate, Float rsiDataPoint, Date timestamp) {
        this.bondIsin = bondIsin;
        this.aggregate = aggregate;
        this.rsiDataPoint = rsiDataPoint;
        this.timestamp = timestamp;
    }

    public String getBondIsin() {
        return bondIsin;
    }

    public Float getRsiDataPoint() {
        return rsiDataPoint;
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
                ", rsiDataPoint=" + rsiDataPoint +
                ", aggregate='" + aggregate + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
