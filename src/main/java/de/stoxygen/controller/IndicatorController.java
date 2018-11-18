package de.stoxygen.controller;

import de.stoxygen.model.Indicator;
import de.stoxygen.repository.IndicatorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@Controller
public class IndicatorController {
    private static final Logger logger = LoggerFactory.getLogger(IndicatorController.class);

    @Autowired
    private IndicatorRepository indicatorRepository;

    @RequestMapping(value = "/indicators", method = RequestMethod.GET)
    public String getAllIndicators(Model model) {
        model.addAttribute("title", "Indicators");
        model.addAttribute("indicators", indicatorRepository.findAll());

        return "indicators";
    }

}
