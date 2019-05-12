package de.stoxygen.model.macd;

import de.stoxygen.model.Auditable;
import de.stoxygen.model.IndicatorConfiguration;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;


@Entity
public class MacdData extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer macdDataId;

    @Column(nullable = false)
    private Date timestamp;

    @Column
    private Float macdSignal;

    @Column(nullable = false)
    private Float macdDataPoint;

    @Column(nullable = false)
    private String aggregate;


    @ManyToOne
    private IndicatorConfiguration indicatorConfiguration;

    public MacdData() {}

    public MacdData(Date timestamp, Float macdDataPoint) {
        this.timestamp = timestamp;
        this.macdDataPoint = macdDataPoint;
    }

    public MacdData(ZonedDateTime timestamp, Float macdDataPoint, IndicatorConfiguration indicatorConfiguration, String aggregate, Float macdSignal) {
        this.macdDataPoint = macdDataPoint;
        this.timestamp = Date.from(timestamp.toInstant());
        this.indicatorConfiguration = indicatorConfiguration;
        this.aggregate = aggregate;
        this.macdSignal = macdSignal;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public Float getMacdSignal() {
        return macdSignal;
    }

    public Float getMacdDataPoint() {
        return macdDataPoint;
    }

    public String getAggregate() {
        return aggregate;
    }

    public void addIndicatorConfiguration(IndicatorConfiguration indicatorConfiguration) {
        this.indicatorConfiguration = indicatorConfiguration;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public void setMacdSignal(Float macdSignal) {
        this.macdSignal = macdSignal;
    }

    public void setMacdDataPoint(Float macdDataPoint) {
        this.macdDataPoint = macdDataPoint;
    }

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    @Override
    public String toString() {
        return "MacdData{" +
                "macdDataId=" + macdDataId +
                ", timestamp=" + timestamp +
                ", macdSignal=" + macdSignal +
                ", macdDataPoint=" + macdDataPoint +
                ", aggregate='" + aggregate + '\'' +
                ", indicatorConfiguration=" + indicatorConfiguration +
                '}';
    }
}
