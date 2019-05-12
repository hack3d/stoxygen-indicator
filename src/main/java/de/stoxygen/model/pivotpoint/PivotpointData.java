package de.stoxygen.model.pivotpoint;

import de.stoxygen.model.Auditable;
import de.stoxygen.model.IndicatorConfiguration;
import org.ta4j.core.indicators.pivotpoints.FibonacciReversalIndicator;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Date;


@Entity
public class PivotpointData extends Auditable<String> {

    @Id
    @Column
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer pivotpointDataId;

    @Column(nullable = false)
    private Date timestamp;

    @Column(nullable = false)
    private FibonacciReversalIndicator.FibReversalTyp pivotpointReversalTyp;

    @Column(nullable = false)
    private FibonacciReversalIndicator.FibonacciFactor pivotpointFibonacciFactor;

    @Column(nullable = false)
    private Float pivotpointDataPoint;

    @Column(nullable = false)
    private String aggregate;


    @ManyToOne
    private IndicatorConfiguration indicatorConfiguration;

    public PivotpointData() {}


    public PivotpointData(ZonedDateTime timestamp, IndicatorConfiguration indicatorConfiguration, String aggregate,
                          FibonacciReversalIndicator.FibReversalTyp pivotpointReversalTyp,
                          FibonacciReversalIndicator.FibonacciFactor pivotpointFibonacciFactor, Float pivotpointDataPoint) {
        this.timestamp = Date.from(timestamp.toInstant());
        this.indicatorConfiguration = indicatorConfiguration;
        this.aggregate = aggregate;
        this.pivotpointDataPoint = pivotpointDataPoint;
        this.pivotpointFibonacciFactor = pivotpointFibonacciFactor;
        this.pivotpointReversalTyp = pivotpointReversalTyp;
    }

    public Integer getPivotpointDataId() {
        return pivotpointDataId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public FibonacciReversalIndicator.FibReversalTyp getPivotpointReversalTyp() {
        return pivotpointReversalTyp;
    }

    public void setPivotpointReversalTyp(FibonacciReversalIndicator.FibReversalTyp pivotpointReversalTyp) {
        this.pivotpointReversalTyp = pivotpointReversalTyp;
    }

    public FibonacciReversalIndicator.FibonacciFactor getPivotpointFibonacciFactor() {
        return pivotpointFibonacciFactor;
    }

    public void setPivotpointFibonacciFactor(FibonacciReversalIndicator.FibonacciFactor pivotpointFibonacciFactor) {
        this.pivotpointFibonacciFactor = pivotpointFibonacciFactor;
    }

    public Float getPivotpointDataPoint() {
        return pivotpointDataPoint;
    }

    public void setPivotpointDataPoint(Float pivotpointDataPoint) {
        this.pivotpointDataPoint = pivotpointDataPoint;
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
        return "PivotpointData{" +
                "pivotpointDataId=" + pivotpointDataId +
                ", timestamp=" + timestamp +
                ", pivotpointReversalTyp=" + pivotpointReversalTyp +
                ", pivotpointFibonacciFactor=" + pivotpointFibonacciFactor +
                ", pivotpointDataPoint=" + pivotpointDataPoint +
                ", aggregate='" + aggregate + '\'' +
                ", indicatorConfiguration=" + indicatorConfiguration +
                '}';
    }
}
