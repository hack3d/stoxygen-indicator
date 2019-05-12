package de.stoxygen.model.adx;

import de.stoxygen.model.Auditable;
import de.stoxygen.model.IndicatorConfiguration;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
public class AdxData extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long adxDataId;

    @Column(nullable = false)
    private Date timestamp;

    @Column(nullable = false)
    private Float adxDataPoint;

    @Column(nullable = false)
    private String aggregate;

    @ManyToOne
    private IndicatorConfiguration indicatorConfiguration;

    public AdxData() {}

    public AdxData(ZonedDateTime zonedDateTime, Float adxDataPoint, String aggregate, IndicatorConfiguration indicatorConfiguration) {
        this.timestamp = Date.from(zonedDateTime.toInstant());
        this.adxDataPoint = adxDataPoint;
        this.aggregate = aggregate;
        this.indicatorConfiguration = indicatorConfiguration;
    }

    public Long getAdxDataId() {
        return adxDataId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Float getAdxDataPoint() {
        return adxDataPoint;
    }

    public void setAdxDataPoint(Float adxDataPoint) {
        this.adxDataPoint = adxDataPoint;
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
        return "AdxData{" +
                "adxDataId=" + adxDataId +
                ", timestamp=" + timestamp +
                ", adxDataPoint=" + adxDataPoint +
                ", aggregate='" + aggregate + '\'' +
                ", indicatorConfiguration=" + indicatorConfiguration +
                '}';
    }
}
