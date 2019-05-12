package de.stoxygen.model.pivotpoint;

import org.ta4j.core.indicators.pivotpoints.FibonacciReversalIndicator;

import java.util.Date;

public class BondPivotpointData {

    private String bondIsin;
    private String aggregate;
    private Float pivotpointDataPoint;
    private FibonacciReversalIndicator.FibonacciFactor pivotpointFibonacciFactor;
    private FibonacciReversalIndicator.FibReversalTyp pivotpointReversalTyp;
    private Date timestamp;

    public BondPivotpointData() {
    }

    public BondPivotpointData(String bondIsin, String aggregate, Date timestamp,
                              FibonacciReversalIndicator.FibReversalTyp pivotpointReversalTyp,
                              FibonacciReversalIndicator.FibonacciFactor pivotpointFibonacciFactor,
                              Float pivotpointDataPoint) {
        this.bondIsin = bondIsin;
        this.aggregate = aggregate;
        this.pivotpointDataPoint = pivotpointDataPoint;
        this.pivotpointFibonacciFactor = pivotpointFibonacciFactor;
        this.pivotpointReversalTyp = pivotpointReversalTyp;
        this.timestamp = timestamp;
    }

    public String getBondIsin() {
        return bondIsin;
    }

    public String getAggregate() {
        return aggregate;
    }

    public Float getPivotpointDataPoint() {
        return pivotpointDataPoint;
    }

    public FibonacciReversalIndicator.FibonacciFactor getPivotpointFibonacciFactor() {
        return pivotpointFibonacciFactor;
    }

    public FibonacciReversalIndicator.FibReversalTyp getPivotpointReversalTyp() {
        return pivotpointReversalTyp;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "BondPivotpointData{" +
                "bondIsin='" + bondIsin + '\'' +
                ", aggregate='" + aggregate + '\'' +
                ", pivotpointDataPoint=" + pivotpointDataPoint +
                ", pivotpointFibonacciFactor=" + pivotpointFibonacciFactor +
                ", pivotpointReversalTyp=" + pivotpointReversalTyp +
                ", timestamp=" + timestamp +
                '}';
    }
}

