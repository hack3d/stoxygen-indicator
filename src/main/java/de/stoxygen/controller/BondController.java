package de.stoxygen.controller;

import de.stoxygen.model.Bond;
import de.stoxygen.model.Indicator;
import de.stoxygen.model.IndicatorBondSetting;
import de.stoxygen.model.IndicatorConfiguration;
import de.stoxygen.model.form.AddIndicatorBondConfigurationForm;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.IndicatorBondSettingRepository;
import de.stoxygen.repository.IndicatorConfigurationRepository;
import de.stoxygen.repository.IndicatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;


@Controller
public class BondController {
    private static final Logger logger = LoggerFactory.getLogger(BondController.class);

    @Autowired
    private BondRepository bondRepository;

    @Autowired
    private IndicatorRepository indicatorRepository;

    @Autowired
    private IndicatorConfigurationRepository indicatorConfigurationRepository;

    @Autowired
    private IndicatorBondSettingRepository indicatorBondSettingRepository;


    @RequestMapping(value = "/bonds", method = RequestMethod.GET)
    public String getAllBonds(Model model) {
        model.addAttribute("bonds", bondRepository.findAll());
        return "bonds";
    }

    /**
     * Show such details for a bond like indicators, exchanges
     * @param isin
     * @param model
     * @return
     */
    @RequestMapping(value = "/bond/{isin}", method = RequestMethod.GET)
    public String getBondByExchangeAndIndicator(@PathVariable(value = "isin") String isin, Model model) {
        Bond bond = bondRepository.findFirstByBondIsin(isin);
        model.addAttribute("title", bond.getBondName());
        model.addAttribute("bond", bond);
        model.addAttribute("exchanges", bond.getExchanges());

        return "bond_detail";
    }

    /**
     * Show form to add indicator configuration for a specific bond.
     * @param isin
     * @param model
     * @return
     */
    @RequestMapping(value = "/bond/{isin}/indicator-configuration/add", method = RequestMethod.GET)
    public String addIndicatorConfiguration(@PathVariable(value = "isin") String isin, AddIndicatorBondConfigurationForm addIndicatorBondConfigurationForm, Model model) {
        model.addAttribute("indicators", indicatorRepository.findAll());
        model.addAttribute("isin", isin);


        return "indicator-configuration_add";
    }

    @RequestMapping(value = "/bond/{isin}/indicator-configuration/add", method = RequestMethod.POST)
    public String addIndicatorConfiguration(@PathVariable(value = "isin") String isin,
                                            @Valid AddIndicatorBondConfigurationForm addIndicatorBondConfigurationForm,
                                            BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            return "bonds";
        }

        logger.debug("AddIndicatorBondConfigurationForm[Isin: {}, IndicatorsId: {}, macdFast: {}, macdSlow: {}, " +
                "bbAvg: {}]", isin, addIndicatorBondConfigurationForm.getIndicatorsId(),
                addIndicatorBondConfigurationForm.getMacdFast(), addIndicatorBondConfigurationForm.getMacdSlow(),
                addIndicatorBondConfigurationForm.getBbAvg());

        // Search for the indicator we selected
        Indicator indicator = indicatorRepository.findOne(addIndicatorBondConfigurationForm.getIndicatorsId());

        // Set new uuid for indicator configuration
        IndicatorConfiguration indicatorConfiguration = new IndicatorConfiguration();
        indicatorConfiguration.addIndicator(indicator);
        indicatorConfigurationRepository.save(indicatorConfiguration);

        // Get new uuid from indicator configuration
        logger.debug("IndicatorConfiguration UUID: {}, Indicator: {}", indicatorConfiguration.getIndicatorConfigurationName(), indicator.getIndicatorName());

        // New IndicatorBondSetting
        Bond bond = bondRepository.findFirstByBondIsin(isin);

        // Now we decide which indicator we want to add
        if(indicator.getIndicatorSymbol().equals("macd")) {
            // MACD fast
            IndicatorBondSetting indicatorBondSetting = new IndicatorBondSetting();
            indicatorBondSetting.addBond(bond);
            indicatorBondSetting.addIndicatorConfiguration(indicatorConfiguration);
            indicatorBondSetting.setIndicatorKey("macdFast");
            indicatorBondSetting.setIndicatorValue(addIndicatorBondConfigurationForm.getMacdFast().toString());
            indicatorBondSettingRepository.save(indicatorBondSetting);

            // MACD slow
            IndicatorBondSetting indicatorBondSetting2 = new IndicatorBondSetting();
            indicatorBondSetting2.addBond(bond);
            indicatorBondSetting2.addIndicatorConfiguration(indicatorConfiguration);
            indicatorBondSetting2.setIndicatorKey("macdSlow");
            indicatorBondSetting2.setIndicatorValue(addIndicatorBondConfigurationForm.getMacdSlow().toString());
            indicatorBondSettingRepository.save(indicatorBondSetting2);

        }

        if(indicator.getIndicatorSymbol().equals("bollinger_bands")) {
            IndicatorBondSetting indicatorBondSetting = new IndicatorBondSetting();
            indicatorBondSetting.addBond(bond);
            indicatorBondSetting.addIndicatorConfiguration(indicatorConfiguration);
            indicatorBondSetting.setIndicatorKey("bbAvg");
            indicatorBondSetting.setIndicatorValue(addIndicatorBondConfigurationForm.getBbAvg().toString());
            indicatorBondSettingRepository.save(indicatorBondSetting);
        }


        return "redirect:/bonds";
    }
}
