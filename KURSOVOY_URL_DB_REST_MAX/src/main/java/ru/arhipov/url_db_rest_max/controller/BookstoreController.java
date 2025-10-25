package ru.arhipov.url_db_rest_max.controller;
import ru.arhipov.url_db_rest_max.entity.Book;
import ru.arhipov.url_db_rest_max.entity.Bookstore;
import ru.arhipov.url_db_rest_max.service.BookstoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/bookstores")
public class BookstoreController {

    private final BookstoreService bookstoreService;

    public BookstoreController(BookstoreService bookstoreService){
        this.bookstoreService=bookstoreService;}

    @GetMapping
    public String listBookstores(Model model){
        List<Bookstore>bookstores=bookstoreService.findAll();
        model.addAttribute("bookstores",bookstores);
        return "bookstores/list";}

    @GetMapping("/create")
    public String showCreateForm(Model model){
        model.addAttribute("bookstore",new Bookstore());
        return "bookstores/create";}

    @PostMapping("/create")
    public String createBookstore(@ModelAttribute Bookstore bookstore){
        bookstoreService.save(bookstore);
        return "redirect:/bookstores";}

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id,Model model){
        Bookstore bookstore=bookstoreService.findById(id)
                .orElseThrow(()->new RuntimeException("Bookstore не найден!"));
        model.addAttribute("bookstore",bookstore);
        return "bookstores/edit";}

    @PostMapping("/edit/{id}")
    public String updateBookstore(@PathVariable Long id,@ModelAttribute Bookstore bookstore){
        bookstore.setId(id);
        bookstoreService.save(bookstore);
        return "redirect:/bookstores";}

    @GetMapping("/delete/{id}")
    public String deleteBookstore(@PathVariable Long id){
        bookstoreService.findById(id);
        return "redirect:/bookstores";}
}
