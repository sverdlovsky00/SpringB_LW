package ru.arhipov.url_db_rest_max.controller;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.arhipov.url_db_rest_max.service.BookService;
import ru.arhipov.url_db_rest_max.service.BookstoreService;
import ru.arhipov.url_db_rest_max.service.CalculationService;
import ru.arhipov.url_db_rest_max.service.UserService;

import java.math.BigDecimal;

@Controller
public class MainController {
    private final BookService bookService;
    private final BookstoreService bookstoreService;
    private final UserService userService;
    private final CalculationService calculationService;

    public MainController(BookService bookService,
                          BookstoreService bookstoreService,
                          UserService userService,
                          CalculationService calculationService) {
        this.bookService = bookService;
        this.bookstoreService = bookstoreService;
        this.userService = userService;
        this.calculationService = calculationService;
    }

    @GetMapping("/")
    public String home(Model model) {
        //данные для счетчиков
        model.addAttribute("totalBooks", bookService.count());
        model.addAttribute("totalBookstores", bookstoreService.count());
        model.addAttribute("totalUsers", userService.findAll().size());

        //юзаем метод calculateTotalBooksValue()
        BigDecimal totalValue = calculationService.calculateTotalBooksValue();
        //BigDecimal в строку для отображения
        String formattedValue = totalValue != null ? totalValue.toString() : "0.00";
        model.addAttribute("totalValue", formattedValue);

        return "home";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}