package de.stoxygen.services;

import com.rabbitmq.client.Channel;
import de.stoxygen.RestfulClient;
import de.stoxygen.configuration.StoxygenIndicatorConfiguration;
import de.stoxygen.model.AggregatedBond;
import de.stoxygen.model.Bond;
import de.stoxygen.model.IndicatorBondSetting;
import de.stoxygen.model.IndicatorCalculateMessage;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.IndicatorBondSettingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.ta4j.core.indicators.MACDIndicator;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
                            ArrayList<String> indicators = new ArrayList<>();
                            for (IndicatorBondSetting indicatorBondSetting : indicatorBondSettingList) {
                                if (indicators.contains(indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorSymbol())) {
                                    logger.info("We already found indicator {} for bond {}", indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorName(), bond.getBondIsin());
                                } else {
                                    logger.info("Add indicator {} to list", indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorName());
                                    indicators.add(indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorSymbol());
                                }
                            }

                            for (String g : indicators) {
                                logger.info("Indicator {} has to be calculate.", g);
                                if (g.equals("macd")) {
                                    Future<MACDIndicator> macd = indicatorService.calculateMacd(data, 9, 12, 26);
                                    while(!macd.isDone()) {
                                        logger.debug("MACD calculation is not finished yet! Waiting...");
                                    }
                                    logger.info("Finished calculate {}", g);
                                    for(int i=0; i < macd.get().getTimeSeries().getBarCount(); i++) {
                                        logger.debug("MACD[Count: {}; Value: {}]", i, macd.get().getValue(i));
                                    }
                                }
                                channel.basicAck(tag, true);
                            }
                        }
                    } else {
                        logger.warn("No data found for bond: {}, timestamp: {}, aggregate: {}", message.getBond(), message.getTimestamp(), message.getAggregate());
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
