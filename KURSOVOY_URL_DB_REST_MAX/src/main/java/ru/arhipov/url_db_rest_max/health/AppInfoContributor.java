package ru.arhipov.url_db_rest_max.health;
import ru.arhipov.url_db_rest_max.service.BookService;
import ru.arhipov.url_db_rest_max.service.BookstoreService;
import ru.arhipov.url_db_rest_max.service.UserService;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class AppInfoContributor implements InfoContributor{

    private final BookService bookService;
    private final BookstoreService bookstoreService;
    private final UserService userService;

    public AppInfoContributor(BookService bookService, BookstoreService bookstoreService,
                UserService userService){
        this.bookService=bookService;this.bookstoreService=bookstoreService;
                this.userService=userService;}

    @Override
    public void contribute(Info.Builder builder) {
        Map<String, Object> appDetails = new HashMap<>();
        appDetails.put("totalBooks", bookService.count());
        appDetails.put("totalBookstores", bookstoreService.count());
        appDetails.put("totalUsers", userService.findAll().size());
        appDetails.put("totalBooksValue", bookService.calculateTotalBooksValue());

        Map<String, Object> status = new HashMap<>();
        status.put("status", "RUNNING");status.put("database", "H2");

        builder.withDetail("application", appDetails).withDetail("system", status);}
}