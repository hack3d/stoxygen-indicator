package de.stoxygen.services;

import de.stoxygen.model.AggregatedBond;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Future;

@Service
public class IndicatorService {
    private static final Logger logger = LoggerFactory.getLogger(IndicatorService.class);

    @Async
    public Future<MACDIndicator> calculateMacd(List<AggregatedBond> data, Integer signal, Integer fast_macd, Integer slow_macd) {
        List<Bar> bars = new ArrayList<>();
        for (AggregatedBond aggregatedBond : data) {
            logger.debug("Raw data[Date: {}, Open: {}, Close: {}]", aggregatedBond.getInsertTimestamp(), aggregatedBond.getOpen(), aggregatedBond.getClose());
            ZonedDateTime d = ZonedDateTime.from(aggregatedBond.getInsertTimestamp().toInstant().atZone(ZoneId.of("UTC")));
            Double open = Double.valueOf(aggregatedBond.getOpen().toString());
            Double high = Double.valueOf(aggregatedBond.getHigh().toString());
            Double low = Double.valueOf(aggregatedBond.getLow().toString());
            Double close = Double.valueOf(aggregatedBond.getClose().toString());
            Double volume = Double.valueOf(aggregatedBond.getVolume().toString());

            bars.add(new BaseBar(d, open, high, low, close, volume));
            logger.debug("BarData[Open: {}, High: {}, Low: {}, Close: {}, Volume: {}, Date: {}", open, high, low, close, volume, d);
        }
        TimeSeries series = new BaseTimeSeries("test", bars);
        logger.debug("TimeSeries[{}]", series.getBarCount());

        logger.debug("ClosePriceIndicator started");
        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
        logger.debug("ClosePriceIndicator is finished.");
        logger.debug("MACDIndicator started.");
        MACDIndicator macdIndicator = new MACDIndicator(closePriceIndicator, signal, slow_macd);
        logger.debug("MACDIndicator is finished.");

        return new AsyncResult<MACDIndicator>(macdIndicator);

    }
}
