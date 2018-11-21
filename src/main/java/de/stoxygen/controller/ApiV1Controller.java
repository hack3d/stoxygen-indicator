package de.stoxygen.controller;

import de.stoxygen.model.*;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.IndicatorBondSettingRepository;
import de.stoxygen.repository.IndicatorConfigurationRepository;
import de.stoxygen.repository.MacdDataRepository;
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
    private IndicatorBondSettingRepository indicatorBondSettingRepository;

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private IndicatorConfigurationRepository indicatorConfigurationRepository;


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
            if(indicatorConfiguration.getIndicators().getIndicatorSymbol().equals("macd")) {
                logger.info("Found a indicator macd for bond {}", bond.getBondIsin());
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
}
