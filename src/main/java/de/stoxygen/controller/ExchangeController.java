package de.stoxygen.controller;

import de.stoxygen.model.Exchange;
import de.stoxygen.repository.BondRepository;
import de.stoxygen.repository.ExchangeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ExchangeController {
    private static final Logger logger = LoggerFactory.getLogger(ExchangeController.class);

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private BondRepository bondRepository;

    @RequestMapping(value = "/exchanges", method = RequestMethod.GET)
    public String getAllExchanges(Model model) {
        model.addAttribute("exchanges", exchangeRepository.findAll());
        return "exchanges";
    }

    @RequestMapping(value = "/exchange/{symbol}/bonds", method = RequestMethod.GET)
    public String getAllBondsByExchange(@PathVariable(value = "symbol") String symbol, Model model) {
        Exchange exchange = exchangeRepository.findFirstBySymbol(symbol);

        model.addAttribute("title", exchange.getName());
        model.addAttribute("bonds", exchange.getBonds());

        return "bonds";
    }
}
