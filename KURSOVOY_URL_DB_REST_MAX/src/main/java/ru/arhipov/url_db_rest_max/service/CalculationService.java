package ru.arhipov.url_db_rest_max.service;
import ru.arhipov.url_db_rest_max.entity.Book;
import ru.arhipov.url_db_rest_max.entity.Bookstore;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CalculationService {

    private final BookService bookService;
    private final BookstoreService bookstoreService;

    public CalculationService(BookService bookService,
                              BookstoreService bookstoreService){
        this.bookService=bookService;this.bookstoreService=bookstoreService;}
    //Расчет стоимости всех книг
    public BigDecimal calculateTotalBooksValue(){
        return bookService.findAll().stream().map(Book::getPrice)
        .reduce(BigDecimal.ZERO,BigDecimal::add).setScale(2,RoundingMode.HALF_UP);}
    //Расчет цены средней книги
    public BigDecimal calculateAverageBookPrice(){
        List<Book>books=bookService.findAll();
        if (books.isEmpty()){return BigDecimal.ZERO;}
        BigDecimal total=calculateTotalBooksValue();
        return total.divide(BigDecimal.valueOf(books.size()),2,RoundingMode.HALF_UP);}
    //Расчет самой дорогой книги
    public Book findMostExpensiveBook(){return bookService.findAll().stream()
            .max((b1,b2)->b1.getPrice().compareTo(b2.getPrice())).orElse(null);}
    //Расчет самой дешевой книги
    public Book findCheapestBook(){return bookService.findAll().stream()
            .min((b1,b2)->b1.getPrice().compareTo(b2.getPrice())).orElse(null);}
    //Расчет количества книг по магазам
    public Map<String,Long>calculateBooksPerBookstore(){
        return bookService.findAll().stream()
                .filter(book -> book.getBookstore() != null)
                .collect(Collectors.groupingBy(
                        book -> book.getBookstore().getName(),
                        Collectors.counting()
                ));}
    //Считаем общую стоимость книг по магазам
    public Map<String,BigDecimal>calculateValuePerBookstore(){
        return bookService.findAll().stream()
                .filter(book -> book.getBookstore() != null)
                .collect(Collectors.groupingBy(book -> book.getBookstore().getName(),
                Collectors.reducing(BigDecimal.ZERO,Book::getPrice,BigDecimal::add)))
                .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                entry -> entry.getValue().setScale(2, RoundingMode.HALF_UP)));}
    //Считаем среднюю цену книги по магазам
    public Map<String, BigDecimal> calculateAveragePricePerBookstore() {
        Map<String, List<Book>> booksByStore = bookService.findAll().stream()
                .filter(book -> book.getBookstore() != null)
                .collect(Collectors.groupingBy(book -> book.getBookstore().getName()));
        return booksByStore.entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,entry -> {
                            List<Book> books = entry.getValue();
                            if (books.isEmpty()) {return BigDecimal.ZERO;}
                            BigDecimal total = books.stream()
                                    .map(Book::getPrice)
                                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                            return total.divide(BigDecimal.valueOf(books.size()), 2, RoundingMode.HALF_UP);}
                ));}
    //Считаем стат по авторам
    public Map<String, Long> calculateBooksPerAuthor() {
        return bookService.findAll().stream()
                .collect(Collectors.groupingBy(Book::getAuthor,Collectors.counting()));}
    //Посчитаем оборот за год
    public BigDecimal calculateAnnualTurnover() {//допустим, каждая книга продается 5 раз в год в среднем
        BigDecimal totalValue=calculateTotalBooksValue();
        return totalValue.multiply(BigDecimal.valueOf(5)).setScale(2, RoundingMode.HALF_UP);}
    //Считаем рентабельность
    public BigDecimal calculateProfitability(){//берем 30 процентов с потолка
        BigDecimal totalValue=calculateTotalBooksValue();
        return totalValue.multiply(BigDecimal.valueOf(0.3))
                .setScale(2,RoundingMode.HALF_UP);}
}
