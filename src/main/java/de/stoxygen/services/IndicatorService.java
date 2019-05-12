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
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.pivotpoints.FibonacciReversalIndicator;
import org.ta4j.core.indicators.pivotpoints.PivotPointIndicator;
import org.ta4j.core.indicators.pivotpoints.TimeLevel;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.Future;

@Service
public class IndicatorService {
    private static final Logger logger = LoggerFactory.getLogger(IndicatorService.class);

    /**
     * Calculate MACD indicator
     * @param data
     * @param fast_macd
     * @param slow_macd
     * @return
     */
    @Async
    public Future<MACDIndicator> calculateMacd(List<AggregatedBond> data, Integer fast_macd, Integer slow_macd) {
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
        MACDIndicator macdIndicator = new MACDIndicator(closePriceIndicator, fast_macd, slow_macd);
        logger.debug("MACDIndicator is finished.");

        return new AsyncResult<MACDIndicator>(macdIndicator);

    }

    /**
     * Calculate EMA indicator
     * @param data
     * @param length
     * @return
     */
    @Async
    public Future<EMAIndicator> calculateEma(List<AggregatedBond> data, Integer length) {

        List<Bar> bars = new ArrayList<>();
        for(AggregatedBond aggregatedBond : data) {
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
        logger.debug("ClosePriceIndicator is finished");
        logger.debug("EMAIndicator started");
        EMAIndicator emaIndicator = new EMAIndicator(closePriceIndicator, length);
        logger.debug("EMAIndicator is finished.");


        return new AsyncResult<EMAIndicator>(emaIndicator);
    }

    public Future<ATRIndicator> calculateAtr(List<AggregatedBond> data, Integer length) {
        List<Bar> bars = new ArrayList<>();
        for(AggregatedBond aggregatedBond : data) {
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


        logger.debug("ATRIndicator started");
        ATRIndicator atrIndicator = new ATRIndicator(series, length);
        logger.debug("ATRIndicator is finished");

        return new AsyncResult<ATRIndicator>(atrIndicator);
    }

    public Future<ADXIndicator> calculateAdx(List<AggregatedBond> data, Integer length) {
        List<Bar> bars = new ArrayList<>();
        for(AggregatedBond aggregatedBond : data) {
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

        logger.debug("ADXIndicator started");
        ADXIndicator adxIndicator = new ADXIndicator(series, length);
        logger.debug("ADXIndicator is finished");

        return new AsyncResult<ADXIndicator>(adxIndicator);
    }

    public Future<RSIIndicator> calculateRsi(List<AggregatedBond> data, Integer length) {
        List<Bar> bars = new ArrayList<>();
        for(AggregatedBond aggregatedBond : data) {
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
        logger.debug("ClosePriceIndicator is finished");
        logger.debug("RSIIndicator started");
        RSIIndicator rsiIndicator = new RSIIndicator(closePriceIndicator, length);
        logger.debug("RSIIndicator is finished");

        return new AsyncResult<RSIIndicator>(rsiIndicator);
    }

    /**
     * PivotPoints with resistence factor 1
     * @param data
     * @return
     */
    public Future<FibonacciReversalIndicator> calculatePivotPointResistence1(List<AggregatedBond> data) {
        List<Bar> bars = new ArrayList<>();
        for(AggregatedBond aggregatedBond : data) {
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

        logger.debug("PivotPointIndicator started");
        PivotPointIndicator pivotPointIndicator = new PivotPointIndicator(series, TimeLevel.DAY);
        logger.debug("PivotPointIndicator is finished");
        logger.debug("FibonacciReversalIndicator started");
        FibonacciReversalIndicator fibonacciReversalIndicator = new FibonacciReversalIndicator(pivotPointIndicator, FibonacciReversalIndicator.FibonacciFactor.Factor1, FibonacciReversalIndicator.FibReversalTyp.RESISTANCE);
        logger.debug("FibonacciReversalIndicator is finished");

        return new AsyncResult<FibonacciReversalIndicator>(fibonacciReversalIndicator);
    }
}
