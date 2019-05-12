package de.stoxygen.services;

import com.rabbitmq.client.Channel;
import de.stoxygen.RestfulClient;
import de.stoxygen.configuration.StoxygenIndicatorConfiguration;
import de.stoxygen.model.*;
import de.stoxygen.model.adx.AdxData;
import de.stoxygen.model.atr.AtrData;
import de.stoxygen.model.indicator.IndicatorSymbol;
import de.stoxygen.model.macd.MacdData;
import de.stoxygen.model.rsi.RsiData;
import de.stoxygen.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;


import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Component
public class EventReceiver {
        private static final Logger logger = LoggerFactory.getLogger(EventReceiver.class);

        @Autowired
        private RestfulClient restfulClient;

        @Autowired
        private StoxygenIndicatorConfiguration stoxygenIndicatorConfiguration;

        @Autowired
        private IndicatorBondSettingRepository indicatorBondSettingRepository;

        @Autowired
        private BondRepository bondRepository;

        @Autowired
        private IndicatorService indicatorService;

        @Autowired
        private MacdDataRepository macdDataRepository;

        @Autowired
        private AtrDataRepository atrDataRepository;

        @Autowired
        private AdxDataRepository adxDataRepository;

        @Autowired
        private RsiDataRepository rsiDataRepository;


        @RabbitListener(queues = "indicatorQueue")
        public void receiveMessage(final IndicatorCalculateMessage message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
            logger.info("Received <{}>", message.toString());

            try {
                if (message.getAggregate().equals("1min")) {
                    // Do stuff for 1 Minute aggregated data.
                    List<AggregatedBond> data = restfulClient.get1MinAggregatedData(message.getExchange(), message.getBond(), message.getTimestamp().getTime(), stoxygenIndicatorConfiguration.getDownloader_url());
                    logger.debug("Size of 1min aggregates: {}", data.size());

                    if (data.size() > 0) {
                        // Get list of indicators to calculate
                        List<Bond> bondList = bondRepository.findByBondIsin(message.getBond());
                        for (Bond bond : bondList) {
                            List<IndicatorBondSetting> indicatorBondSettingList = indicatorBondSettingRepository.findByBonds(bond);
                            logger.debug("Found {} settings.", indicatorBondSettingList.size());
                            if (indicatorBondSettingList.size() > 0) {
                                ArrayList<IndicatorSymbol> indicators = new ArrayList<>();
                                for (IndicatorBondSetting indicatorBondSetting : indicatorBondSettingList) {
                                    if (indicators.contains(indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorSymbol())) {
                                        logger.info("We already found indicator {} for bond {}", indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorName(), bond.getBondIsin());
                                    } else {
                                        logger.info("Add indicator {} to list", indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorName());
                                        indicators.add(indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorSymbol());
                                    }
                                }

                                for (IndicatorSymbol g : indicators) {
                                    logger.info("Indicator {} has to be calculate.", g);

                                    // RSI
                                    if(g.equals(IndicatorSymbol.RSI)) {
                                        IndicatorBondSetting rsi_setting = indicatorBondSettingRepository.
                                                findByIndicatorKeyAndBonds("rsiLength", bond);

                                        Integer rsiLength = Integer.valueOf(rsi_setting.getIndicatorValue());

                                        Future<RSIIndicator> rsi = indicatorService.calculateRsi(data, rsiLength);

                                        while(!rsi.isDone()) {
                                            logger.debug("RSI calculation is not finished yet! Waiting...");
                                        }

                                        logger.info("Finished calculation of {}", IndicatorSymbol.RSI);

                                        ArrayList<RsiData> rsiDataArrayList = new ArrayList<>();
                                        for (int i = 0; i < rsi.get().getTimeSeries().getBarCount(); i++) {
                                            logger.debug("RSI[Count: {}; Value: {}; Time: {}]", i,
                                                    rsi.get().getValue(i),
                                                    rsi.get().getTimeSeries().getBar(i).getBeginTime());
                                            rsiDataArrayList.add(new RsiData(
                                                    rsi.get().getTimeSeries().getBar(i).getBeginTime(),
                                                    rsi.get().getValue(i).floatValue(),
                                                    message.getAggregate(), rsi_setting.getIndicatorConfiguration()));
                                        }

                                        if(rsiDataArrayList.size() > 0) {
                                            logger.debug("Remove old entries!");
                                            rsiDataRepository.removeByIndicatorConfigurationAndAggregate(
                                                    rsi_setting.getIndicatorConfiguration(), "1min");

                                            logger.debug("There are some values to save!");
                                            rsiDataRepository.save(rsiDataArrayList);
                                        }
                                    }

                                    // ADX
                                    if(g.equals(IndicatorSymbol.ADX)) {
                                        IndicatorBondSetting adx_setting = indicatorBondSettingRepository.
                                                findByIndicatorKeyAndBonds("adxLength", bond);

                                        Integer adxLength = Integer.valueOf(adx_setting.getIndicatorValue());

                                        Future<ADXIndicator> adx = indicatorService.calculateAdx(data, adxLength);

                                        while(!adx.isDone()) {
                                            logger.debug("ADX calculation is not finished yet! Waiting...");
                                        }

                                        logger.info("Finished calculation of {}", IndicatorSymbol.ADX);

                                        ArrayList<AdxData> adxDataArrayList = new ArrayList<>();
                                        for (int i = 0; i < adx.get().getTimeSeries().getBarCount(); i++) {
                                            logger.debug("ADX[Count: {}; Value: {}; Time: {}]", i,
                                                    adx.get().getValue(i),
                                                    adx.get().getTimeSeries().getBar(i).getBeginTime());
                                            adxDataArrayList.add(new AdxData(
                                                    adx.get().getTimeSeries().getBar(i).getBeginTime(),
                                                    adx.get().getValue(i).floatValue(),
                                                    message.getAggregate(), adx_setting.getIndicatorConfiguration()));
                                        }

                                        if(adxDataArrayList.size() > 0) {
                                            logger.debug("Remove old entries!");
                                            adxDataRepository.removeByIndicatorConfigurationAndAggregate(
                                                    adx_setting.getIndicatorConfiguration(), "1min");

                                            logger.debug("There are some values to save!");
                                            adxDataRepository.save(adxDataArrayList);
                                        }
                                    }

                                    // ATR
                                    if(g.equals(IndicatorSymbol.ATR)) {
                                        IndicatorBondSetting atr_setting = indicatorBondSettingRepository.
                                                findByIndicatorKeyAndBonds("atrLength", bond);

                                        Integer atrLength = Integer.valueOf(atr_setting.getIndicatorValue());

                                        Future<ATRIndicator> atr = indicatorService.calculateAtr(data, atrLength);

                                        while(!atr.isDone()) {
                                            logger.debug("ATR calculation is not finished yet! Waiting...");
                                        }

                                        logger.info("Finished calculation of {}", IndicatorSymbol.ATR);

                                        ArrayList<AtrData> atrDataArrayList = new ArrayList<>();
                                        for (int i = 0; i < atr.get().getTimeSeries().getBarCount(); i++) {
                                            logger.debug("ATR[Count: {}; Value: {}; Time: {}]", i,
                                                    atr.get().getValue(i),
                                                    atr.get().getTimeSeries().getBar(i).getBeginTime());
                                            atrDataArrayList.add(new AtrData(
                                                    atr.get().getTimeSeries().getBar(i).getBeginTime(),
                                                    atr.get().getValue(i).floatValue(),
                                                    message.getAggregate(), atr_setting.getIndicatorConfiguration()));
                                        }

                                        if(atrDataArrayList.size() > 0) {
                                            logger.debug("Remove old entries!");
                                            atrDataRepository.removeByIndicatorConfigurationAndAggregate(
                                                    atr_setting.getIndicatorConfiguration(), "1min");

                                            logger.debug("There are some values to save!");
                                            atrDataRepository.save(atrDataArrayList);
                                        }
                                    }

                                    // MACD
                                    // Fast: 12
                                    // Slow: 26
                                    // Signal: 9
                                    if (g.equals(IndicatorSymbol.MACD)) {
                                        // Get settings for indicator macd
                                        IndicatorBondSetting macd_setting1 = indicatorBondSettingRepository.
                                                findByIndicatorKeyAndBonds("macdFast", bond);
                                        IndicatorBondSetting macd_setting2 = indicatorBondSettingRepository.
                                                findByIndicatorKeyAndBonds("macdSlow", bond);

                                        Integer macdFast = Integer.valueOf(macd_setting1.getIndicatorValue());
                                        Integer macdSlow = Integer.valueOf(macd_setting2.getIndicatorValue());

                                        // calc MACD
                                        Future<MACDIndicator> macd = indicatorService.calculateMacd(data,
                                                macdFast, macdSlow);

                                        while (!macd.isDone()) {
                                            logger.debug("MACD calculation is not finished yet! Waiting...");
                                        }

                                        // calc signal
                                        List<AggregatedBond> emaRaw = new ArrayList<>();
                                        for(int i = 0; i < macd.get().getTimeSeries().getBarCount(); i++) {
                                            emaRaw.add(new AggregatedBond(
                                                    macd.get().getTimeSeries().getBar(i).getBeginTime(),
                                                    macd.get().getValue(i).floatValue()));

                                        }
                                        Future<EMAIndicator> ema = indicatorService.calculateEma(emaRaw, 9);

                                        while(!ema.isDone()) {
                                            logger.debug("EMA calculation is not finished yet! Waiting...");
                                        }

                                        logger.info("Finished calculate {}", g);
                                        ArrayList<MacdData> macdDataArrayList = new ArrayList<>();

                                        for (int i = 0; i < macd.get().getTimeSeries().getBarCount(); i++) {
                                            logger.debug("MACD[Count: {}; Value: {}; Time: {}], Signal[Count: {}, Value: {}, Time: {}]", i,
                                                    macd.get().getValue(i),
                                                    macd.get().getTimeSeries().getBar(i).getBeginTime(), i,
                                                    ema.get().getValue(i), ema.get().getTimeSeries().getBar(i).getBeginTime());
                                            macdDataArrayList.add(new MacdData(
                                                    macd.get().getTimeSeries().getBar(i).getBeginTime(),
                                                    macd.get().getValue(i).floatValue(),
                                                    macd_setting1.getIndicatorConfiguration(), message.getAggregate(),
                                                    ema.get().getValue(i).floatValue()));
                                        }

                                        if(macdDataArrayList.size() > 0) {
                                            logger.debug("Remove old entries!");
                                            macdDataRepository.removeByIndicatorConfigurationAndAggregate(
                                                            macd_setting1.getIndicatorConfiguration(), "1min");

                                            logger.debug("There are some values to save!");
                                            macdDataRepository.save(macdDataArrayList);
                                        }
                                    }
                                    logger.info("Acknowledge message on queue.");
                                    channel.basicAck(tag, true);
                                }
                            } else {
                                logger.info("No indicator setting for bond {} found.", message.getBond());
                                channel.basicAck(tag, true);
                            }
                        }
                    } else {
                        logger.warn("No data found for bond: {}, timestamp: {}, aggregate: {}", message.getBond(),
                                message.getTimestamp(), message.getAggregate());
                        channel.basicAck(tag, true);
                    }
                }
            } catch (ResourceAccessException e) {
                logger.warn("Something went wrong!");
                channel.basicReject(tag, true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
}
