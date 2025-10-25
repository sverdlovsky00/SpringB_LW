package ru.arhipov.url_db_rest_max.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.arhipov.url_db_rest_max.entity.Book;
import ru.arhipov.url_db_rest_max.entity.Bookstore;
import ru.arhipov.url_db_rest_max.service.BookService;
import ru.arhipov.url_db_rest_max.service.BookstoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final BookstoreService bookstoreService;

    public BookController(BookService bookService, BookstoreService bookstoreService) {
        this.bookService = bookService;
        this.bookstoreService = bookstoreService;
    }

    @GetMapping
    public String listBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        return "books/list";
    }

    @GetMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("bookstores", bookstoreService.findAll());
        return "books/create";
    }

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String createBook(@ModelAttribute Book book,
                             @RequestParam(value = "bookstoreId", required = false) Long bookstoreId) {
        // Устанавливаем связь с магазином
        if (bookstoreId != null) {
            Bookstore bookstore = bookstoreService.findById(bookstoreId).orElse(null);
            book.setBookstore(bookstore);
        }
        bookService.save(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        model.addAttribute("book", book);
        model.addAttribute("bookstores", bookstoreService.findAll());
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String updateBook(@PathVariable Long id,
                             @ModelAttribute Book book,
                             @RequestParam(value = "bookstoreId", required = false) Long bookstoreId) {

        // Находим существующую книгу
        Book existingBook = bookService.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Обновляем поля
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setIsbn(book.getIsbn());
        existingBook.setPrice(book.getPrice());

        // Обновляем связь с магазином
        if (bookstoreId != null) {
            Bookstore bookstore = bookstoreService.findById(bookstoreId).orElse(null);
            existingBook.setBookstore(bookstore);
        } else {
            existingBook.setBookstore(null);
        }

        bookService.save(existingBook);
        return "redirect:/books";
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return "redirect:/books";
    }
}