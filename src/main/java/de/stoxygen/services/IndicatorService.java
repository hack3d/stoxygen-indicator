package de.stoxygen.services;

import de.stoxygen.model.AggregatedBond;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class IndicatorService {
    private static final Logger logger = LoggerFactory.getLogger(IndicatorService.class);

    @Async
    public MACDIndicator calculateMacd(List<AggregatedBond> data, Integer signal, Integer fast_macd, Integer slow_macd) {
        List<Bar> bars = new ArrayList<>();
        for(AggregatedBond aggregatedBond : data) {
            ZonedDateTime d = ZonedDateTime.from(aggregatedBond.getInsertTimestamp().toInstant().atZone(ZoneId.of("UTC")));
            Double open = Double.valueOf(aggregatedBond.getOpen().toString());
            Double high = Double.valueOf(aggregatedBond.getHigh().toString());
            Double low = Double.valueOf(aggregatedBond.getLow().toString());
            Double close = Double.valueOf(aggregatedBond.getClose().toString());
            Double volume = Double.valueOf(aggregatedBond.getVolume().toString());

            bars.add(new BaseBar(d, open, high, low, close, volume));
        }
        TimeSeries series = new BaseTimeSeries("test", bars);

        ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(series);
        MACDIndicator macdIndicator = new MACDIndicator(closePriceIndicator, signal, slow_macd);


        return macdIndicator;
    }
}