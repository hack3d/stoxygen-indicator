package de.stoxygen.model.atr;

import de.stoxygen.model.Auditable;
import de.stoxygen.model.IndicatorConfiguration;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class AtrData extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long atrDataId;

    @Column(nullable = false)
    private Date timestamp;

    @Column(nullable = false)
    private Float atrDataPoint;

    @Column(nullable = false)
    private String aggregate;

    @ManyToOne
    private IndicatorConfiguration indicatorConfiguration;

    public AtrData() {}

    public AtrData(ZonedDateTime zonedDateTime, Float atrDataPoint, String aggregate, IndicatorConfiguration indicatorConfiguration) {
        this.timestamp = Date.from(zonedDateTime.toInstant());
        this.atrDataPoint = atrDataPoint;
        this.aggregate = aggregate;
        this.indicatorConfiguration = indicatorConfiguration;
    }

    public Long getAtrDataId() {
        return atrDataId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Float getAtrDataPoint() {
        return atrDataPoint;
    }

    public void setAtrDataPoint(Float atrDataPoint) {
        this.atrDataPoint = atrDataPoint;
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
                "atrDataId=" + atrDataId +
                ", timestamp=" + timestamp +
                ", atrDataPoint=" + atrDataPoint +
                ", aggregate='" + aggregate + '\'' +
                ", indicatorConfiguration=" + indicatorConfiguration +
                '}';
    }
}
