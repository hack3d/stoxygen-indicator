package de.stoxygen.model;

import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Model for REDIS data
 */
@RedisHash("MacdData")
public class MacdData implements Serializable {

    @Id
    private String id;

    private Date time;

    private Integer exchangeId;

    private String bondIsin;

    private Float macdSignal;

    private Float fastMacd;

    private Float slowMacd;

    public MacdData() {}

    public Date getTime() {
        return time;
    }

    public Integer getExchangeId() {
        return exchangeId;
    }

    public Float getMacdSignal() {
        return macdSignal;
    }

    public Float getFastMacd() {
        return fastMacd;
    }

    public Float getSlowMacd() {
        return slowMacd;
    }
}
