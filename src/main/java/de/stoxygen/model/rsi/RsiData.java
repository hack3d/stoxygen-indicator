package de.stoxygen.model.rsi;

import de.stoxygen.model.Auditable;
import de.stoxygen.model.IndicatorConfiguration;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class RsiData extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long rsiDataId;

    @Column(nullable = false)
    private Date timestamp;

    @Column(nullable = false)
    private Float rsiDataPoint;

    @Column(nullable = false)
    private String aggregate;

    @ManyToOne
    private IndicatorConfiguration indicatorConfiguration;

    public RsiData() {}

    public RsiData(ZonedDateTime zonedDateTime, Float rsiDataPoint, String aggregate, IndicatorConfiguration indicatorConfiguration) {
        this.timestamp = Date.from(zonedDateTime.toInstant());
        this.rsiDataPoint = rsiDataPoint;
        this.aggregate = aggregate;
        this.indicatorConfiguration = indicatorConfiguration;
    }

    public Long getRsiDataId() {
        return rsiDataId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Float getRsiDataPoint() {
        return rsiDataPoint;
    }

    public void setRsiDataPoint(Float rsiDataPoint) {
        this.rsiDataPoint = rsiDataPoint;
    }

    public String getAggregate() {
        return aggregate;
    }

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
    }

    public IndicatorConfiguration getIndicatorConfiguration() {
        return indicatorConfiguration;
    }

    public void setIndicatorConfiguration(IndicatorConfiguration indicatorConfiguration) {
        this.indicatorConfiguration = indicatorConfiguration;
    }

    @Override
    public String toString() {
        return "AtrData{" +
                "rsiDataId=" + rsiDataId +
                ", timestamp=" + timestamp +
                ", rsiDataPoint=" + rsiDataPoint +
                ", aggregate='" + aggregate + '\'' +
                ", indicatorConfiguration=" + indicatorConfiguration +
                '}';
    }
}
