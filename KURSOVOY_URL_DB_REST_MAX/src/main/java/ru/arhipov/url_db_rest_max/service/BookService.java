package ru.arhipov.url_db_rest_max.service;
import ru.arhipov.url_db_rest_max.entity.User;
import ru.arhipov.url_db_rest_max.entity.Book;
import ru.arhipov.url_db_rest_max.repository.BookRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository){this.bookRepository=bookRepository;}

    public List<Book> findAll(){return bookRepository.findAllWithBookstore();}

    public Optional<Book>findById(Long id){return bookRepository.findById(id);}

    public Book save(Book book){return bookRepository.save(book);}

    public void deleteById(Long id){bookRepository.deleteById(id);}

    public List<Book> findByTitle(String title) {return bookRepository
            .findByTitleContainingIgnoreCase(title);}

    public List<Book> findByAuthor(String author) {return bookRepository
            .findByAuthorContainingIgnoreCase(author);}

    public List<Book> findByPriceRange(Double minPrice,Double maxPrice){
        return bookRepository.findByPriceBetween(minPrice,maxPrice);}

    public List<Book> findByUser(User user){return bookRepository.findByCreatedBy(user);}

    public long count(){return bookRepository.count();}
    //Расчет общей стоимости всех книг
    public Double calculateTotalBooksValue(){return bookRepository.findAll().stream()
            .mapToDouble(book->book.getPrice().doubleValue()).sum();}
}
