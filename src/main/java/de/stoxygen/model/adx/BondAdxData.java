package de.stoxygen.model.adx;

import java.util.Date;

public class BondAdxData {

    private String bondIsin;
    private Float adxDataPoint;
    private String aggregate;
    private Date timestamp;

    public BondAdxData() {}

    public BondAdxData(String bondIsin, String aggregate, Float adxDataPoint, Date timestamp) {
        this.bondIsin = bondIsin;
        this.aggregate = aggregate;
        this.adxDataPoint = adxDataPoint;
        this.timestamp = timestamp;
    }

    public String getBondIsin() {
        return bondIsin;
    }

    public Float getAdxDataPoint() {
        return adxDataPoint;
    }

    public String getAggregate() {
        return aggregate;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "BondAdxData{" +
                "bondIsin='" + bondIsin + '\'' +
                ", adxDataPoint=" + adxDataPoint +
                ", aggregate='" + aggregate + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
