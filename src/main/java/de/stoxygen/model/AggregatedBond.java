package de.stoxygen.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.nio.channels.FileLock;
import java.time.ZonedDateTime;
import java.util.Date;

public class AggregatedBond {

    private Long tickdata1MinuteId;
    private Float open;
    private Float close;
    private Float bid;
    private Float ask;
    private Float high;
    private Float low;
    private Float volume;
    @JsonIgnore
    private Boolean aggregated;
    private Date insertTimestamp;
    private Date modifyTimestamp;

    public Long getTickdata1MinuteId() {
            return tickdata1MinuteId;
        }

    public Float getOpen() {
            return open;
        }

    public void setOpen(Float open) {
            this.open = open;
        }

    public Float getClose() {
            return close;
        }

    public void setClose(Float close) {
            this.close = close;
        }

    public Float getBid() {
            return bid;
        }

    public void setBid(Float bid) {
            this.bid = bid;
        }

    public Float getAsk() {
            return ask;
        }

    public void setAsk(Float ask) {
            this.ask = ask;
        }

    public Float getHigh() {
            return high;
        }

    public void setHigh(Float high) {
            this.high = high;
        }

    public Float getLow() {
            return low;
        }

    public void setLow(Float low) {
            this.low = low;
        }

    public Float getVolume() {
            return volume;
        }

    public void setVolume(Float volume) {
            this.volume = volume;
        }

    public Boolean getAggregated() {
            return aggregated;
        }

    public void setAggregated(Boolean aggregated) {
            this.aggregated = aggregated;
        }

    public Date getInsertTimestamp() {
        return insertTimestamp;
    }

    public void setInsertTimestamp(Date insertTimestamp) {
        this.insertTimestamp = insertTimestamp;
    }

    public Date getModifyTimestamp() {
        return modifyTimestamp;
    }

    public void setModifyTimestamp(Date modifyTimestamp) {
        this.modifyTimestamp = modifyTimestamp;
    }

    public AggregatedBond() {    }

    public AggregatedBond(ZonedDateTime insertTimestamp, Float close) {
        this.aggregated = false;
        this.open = Float.valueOf(0);
        this.close = close;
        this.ask = Float.valueOf(0);
        this.bid = Float.valueOf(0);
        this.modifyTimestamp = new Date();
        this.high = Float.valueOf(0);
        this.low = Float.valueOf(0);
        this.volume = Float.valueOf(0);
        this.insertTimestamp = Date.from(insertTimestamp.toInstant());
    }

    @Override
    public String toString() {
        String info = String.format("TickdataCurrent[Id: {}, Open: {}, Close: {} Bid: {}, Ask: {}, High: {}, Low: {}, " +
                            "Volume: {}, Aggregated: {}", tickdata1MinuteId, open, close, bid, ask, high, low,
                    volume, aggregated);
        return info;
    }
}
