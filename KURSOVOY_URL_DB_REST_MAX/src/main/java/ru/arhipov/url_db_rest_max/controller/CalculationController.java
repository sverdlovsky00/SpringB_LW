package ru.arhipov.url_db_rest_max.controller;
import ru.arhipov.url_db_rest_max.service.CalculationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.math.BigDecimal;
import java.util.Map;

@Controller
@RequestMapping("/calculations")
@PreAuthorize("isAuthenticated()")
public class CalculationController {
    private final CalculationService calculationService;

    public CalculationController(CalculationService calculationService) {
        this.calculationService = calculationService;
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model) {
        model.addAttribute("totalBooks", calculationService.calculateTotalBooksValue());
        model.addAttribute("averagePrice", calculationService.calculateAverageBookPrice());
        model.addAttribute("mostExpensiveBook", calculationService.findMostExpensiveBook());
        model.addAttribute("cheapestBook", calculationService.findCheapestBook());
        model.addAttribute("annualTurnover", calculationService.calculateAnnualTurnover());
        model.addAttribute("profitability", calculationService.calculateProfitability());

        //стат по магазам
        model.addAttribute("booksPerBookstore", calculationService.calculateBooksPerBookstore());
        model.addAttribute("valuePerBookstore", calculationService.calculateValuePerBookstore());
        model.addAttribute("averagePricePerBookstore", calculationService.calculateAveragePricePerBookstore());

        //стат по авторам
        model.addAttribute("booksPerAuthor", calculationService.calculateBooksPerAuthor());

        return "calculations/statistics";}
}