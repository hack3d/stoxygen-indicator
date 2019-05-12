package de.stoxygen.controller;

import de.stoxygen.model.*;
import de.stoxygen.model.adx.AdxData;
import de.stoxygen.model.adx.BondAdxData;
import de.stoxygen.model.atr.AtrData;
import de.stoxygen.model.atr.BondAtrData;
import de.stoxygen.model.indicator.IndicatorSymbol;
import de.stoxygen.model.macd.BondMacdData;
import de.stoxygen.model.macd.MacdData;
import de.stoxygen.model.rsi.BondRsiData;
import de.stoxygen.model.rsi.RsiData;
import de.stoxygen.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Controller
@RequestMapping("/api/v1")
public class ApiV1Controller {
    private static final Logger logger = LoggerFactory.getLogger(ApiV1Controller.class);

    @Autowired
    private MacdDataRepository macdDataRepository;

    @Autowired
    private AtrDataRepository atrDataRepository;

    @Autowired
    private AdxDataRepository adxDataRepository;

    @Autowired
    private RsiDataRepository rsiDataRepository;

    @Autowired
    private IndicatorBondSettingRepository indicatorBondSettingRepository;

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private IndicatorConfigurationRepository indicatorConfigurationRepository;


    @RequestMapping(value = "/atrData/isin/{isin}/aggregate/{aggregate}", method = RequestMethod.GET)
    @ResponseBody
    public List<BondAtrData> getBondAtrData(@PathVariable(value = "isin") String isin, @PathVariable(value = "aggregate") String aggregate) {
        List<BondAtrData> bondAtrDataList = new ArrayList<>();

        // Get bond
        Bond bond = bondRepository.findFirstByBondIsin(isin);
        logger.debug("{}", bond.toString());

        // Get all IndicatorBondSettings
        List<IndicatorBondSetting> indicatorBondSettingsList = indicatorBondSettingRepository.findByBonds(bond);
        HashSet<Integer> indicatorHashSet = new HashSet<>();
        for(IndicatorBondSetting indicatorBondSetting : indicatorBondSettingsList) {
            logger.debug("IndicatorBondSetting[Id: {}, Key: {}, Value: {}, Bond: {}, Indicator: {}]",
                    indicatorBondSetting.getIndicatorBondSettingsId(), indicatorBondSetting.getIndicatorKey(),
                    indicatorBondSetting.getIndicatorValue(), indicatorBondSetting.getBonds().getBondIsin(),
                    indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorName());

            // Create unique list of indicators for bond
            indicatorHashSet.add(indicatorBondSetting.getIndicatorConfiguration().getIndicatorConfigurationsId());
        }

        for(Integer indicator : indicatorHashSet) {
            IndicatorConfiguration indicatorConfiguration = indicatorConfigurationRepository.findOne(indicator);
            if(indicatorConfiguration.getIndicators().getIndicatorSymbol().equals(IndicatorSymbol.ATR)) {
                logger.info("Found a indicator {} for bond {}", IndicatorSymbol.ATR, bond.getBondIsin());
                List<AtrData> atrDataList = atrDataRepository.findByIndicatorConfigurationAndAggregate(indicatorConfiguration, aggregate);


                for(AtrData atrData : atrDataList) {
                    bondAtrDataList.add(new BondAtrData(bond.getBondIsin(), atrData.getAggregate(), atrData.getAtrDataPoint(), atrData.getTimestamp()));
                }

                if(bondAtrDataList.size() > 0) {
                    logger.info("Generate list of macd data with {} records and aggregate {}", bondAtrDataList.size(), aggregate);
                }
            }
        }

        return bondAtrDataList;
    }

    @RequestMapping(value = "/macdData/isin/{isin}/aggregate/{aggregate}", method = RequestMethod.GET)
    @ResponseBody
    public List<BondMacdData> getBondMacdData(@PathVariable(value = "isin") String isin, @PathVariable(value = "aggregate") String aggregate) {
        List<BondMacdData> bondMacdDataList = new ArrayList<>();

        // Get bond
        Bond bond = bondRepository.findFirstByBondIsin(isin);
        logger.debug("Bond[Isin: {}, Name: {}]", bond.getBondIsin(), bond.getBondName());

        // Get all IndicatorBondSettings
        List<IndicatorBondSetting> indicatorBondSettingList = indicatorBondSettingRepository.findByBonds(bond);

        HashSet<Integer> indicatorHashSet = new HashSet<>();
        for(IndicatorBondSetting indicatorBondSetting : indicatorBondSettingList) {
            logger.debug("IndicatorBondSetting[Id: {}, Key: {}, Value: {}, Bond: {}, Indicator: {}]",
                    indicatorBondSetting.getIndicatorBondSettingsId(), indicatorBondSetting.getIndicatorKey(),
                    indicatorBondSetting.getIndicatorValue(), indicatorBondSetting.getBonds().getBondIsin(),
                    indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorName());

            // Create unique list of indicators for bond.
            indicatorHashSet.add(indicatorBondSetting.getIndicatorConfiguration().getIndicatorConfigurationsId());
        }

        for(Integer indicator : indicatorHashSet) {
            IndicatorConfiguration indicatorConfiguration = indicatorConfigurationRepository.findOne(indicator);
            if(indicatorConfiguration.getIndicators().getIndicatorSymbol().equals(IndicatorSymbol.MACD)) {
                logger.info("Found a indicator {} for bond {}", IndicatorSymbol.MACD, bond.getBondIsin());
                List<MacdData> macdDataList = macdDataRepository.findByIndicatorConfigurationAndAggregate(indicatorConfiguration, aggregate);


                for(MacdData macdData : macdDataList) {
                    bondMacdDataList.add(new BondMacdData(bond.getBondIsin(), macdData.getAggregate(), macdData.getMacdDataPoint(), macdData.getMacdSignal(), macdData.getTimestamp()));
                }

                if(bondMacdDataList.size() > 0) {
                    logger.info("Generate list of macd data with {} records and aggregate {}", bondMacdDataList.size(), aggregate);
                }
            }
        }


        return bondMacdDataList;
    }

    @RequestMapping(value = "/adxData/isin/{isin}/aggregate/{aggregate}", method = RequestMethod.GET)
    @ResponseBody
    public List<BondAdxData> getBondAdxData(@PathVariable(value = "isin") String isin, @PathVariable(value = "aggregate") String aggregate) {
        List<BondAdxData> bondAdxDataList = new ArrayList<>();

        // Get bond
        Bond bond = bondRepository.findFirstByBondIsin(isin);
        logger.debug("{}", bond.toString());

        // Get all IndicatorBondSettings
        List<IndicatorBondSetting> indicatorBondSettingsList = indicatorBondSettingRepository.findByBonds(bond);
        HashSet<Integer> indicatorHashSet = new HashSet<>();
        for(IndicatorBondSetting indicatorBondSetting : indicatorBondSettingsList) {
            logger.debug("IndicatorBondSetting[Id: {}, Key: {}, Value: {}, Bond: {}, Indicator: {}]",
                    indicatorBondSetting.getIndicatorBondSettingsId(), indicatorBondSetting.getIndicatorKey(),
                    indicatorBondSetting.getIndicatorValue(), indicatorBondSetting.getBonds().getBondIsin(),
                    indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorName());

            // Create unique list of indicators for bond
            indicatorHashSet.add(indicatorBondSetting.getIndicatorConfiguration().getIndicatorConfigurationsId());
        }

        for(Integer indicator : indicatorHashSet) {
            IndicatorConfiguration indicatorConfiguration = indicatorConfigurationRepository.findOne(indicator);
            if(indicatorConfiguration.getIndicators().getIndicatorSymbol().equals(IndicatorSymbol.ADX)) {
                logger.info("Found a indicator {} for bond {}", IndicatorSymbol.ADX, bond.getBondIsin());
                List<AdxData> adxDataList = adxDataRepository.findByIndicatorConfigurationAndAggregate(indicatorConfiguration, aggregate);


                for(AdxData adxData : adxDataList) {
                    bondAdxDataList.add(new BondAdxData(bond.getBondIsin(), adxData.getAggregate(), adxData.getAdxDataPoint(), adxData.getTimestamp()));
                }

                if(bondAdxDataList.size() > 0) {
                    logger.info("Generate list of macd data with {} records and aggregate {}", bondAdxDataList.size(), aggregate);
                }
            }
        }

        return bondAdxDataList;
    }

    @RequestMapping(value = "/rsiData/isin/{isin}/aggregate/{aggregate}", method = RequestMethod.GET)
    @ResponseBody
    public List<BondRsiData> getBondRsiData(@PathVariable(value = "isin") String isin, @PathVariable(value = "aggregate") String aggregate) {
        List<BondRsiData> bondRsiDataList = new ArrayList<>();

        // Get bond
        Bond bond = bondRepository.findFirstByBondIsin(isin);
        logger.debug("{}", bond.toString());

        // Get all IndicatorBondSettings
        List<IndicatorBondSetting> indicatorBondSettingsList = indicatorBondSettingRepository.findByBonds(bond);
        HashSet<Integer> indicatorHashSet = new HashSet<>();
        for(IndicatorBondSetting indicatorBondSetting : indicatorBondSettingsList) {
            logger.debug("IndicatorBondSetting[Id: {}, Key: {}, Value: {}, Bond: {}, Indicator: {}]",
                    indicatorBondSetting.getIndicatorBondSettingsId(), indicatorBondSetting.getIndicatorKey(),
                    indicatorBondSetting.getIndicatorValue(), indicatorBondSetting.getBonds().getBondIsin(),
                    indicatorBondSetting.getIndicatorConfiguration().getIndicators().getIndicatorName());

            // Create unique list of indicators for bond
            indicatorHashSet.add(indicatorBondSetting.getIndicatorConfiguration().getIndicatorConfigurationsId());
        }

        for(Integer indicator : indicatorHashSet) {
            IndicatorConfiguration indicatorConfiguration = indicatorConfigurationRepository.findOne(indicator);
            if(indicatorConfiguration.getIndicators().getIndicatorSymbol().equals(IndicatorSymbol.RSI)) {
                logger.info("Found a indicator {} for bond {}", IndicatorSymbol.RSI, bond.getBondIsin());
                List<RsiData> rsiDataList = rsiDataRepository.findByIndicatorConfigurationAndAggregate(indicatorConfiguration, aggregate);


                for(RsiData rsiData : rsiDataList) {
                    bondRsiDataList.add(new BondRsiData(bond.getBondIsin(), rsiData.getAggregate(), rsiData.getRsiDataPoint(), rsiData.getTimestamp()));
                }

                if(bondRsiDataList.size() > 0) {
                    logger.info("Generate list of macd data with {} records and aggregate {}", bondRsiDataList.size(), aggregate);
                }
            }
        }

        return bondRsiDataList;
    }
}
