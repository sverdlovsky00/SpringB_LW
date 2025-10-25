package ru.arhipov.url_db_rest_max.repository;
import ru.arhipov.url_db_rest_max.entity.User;
import ru.arhipov.url_db_rest_max.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    //найти все книги конкретного пользователя
    List<Book> findByCreatedBy(User user);

    //найти книги по названию
    List<Book> findByTitleContainingIgnoreCase(String title);

    //найти книги по автору
    List<Book> findByAuthorContainingIgnoreCase(String author);

    //найти книги в ценовом диапазоне
    List<Book> findByPriceBetween(Double minPrice, Double maxPrice);

    //все книги с информацией о магазине
    @Query("SELECT b FROM Book b JOIN FETCH b.bookstore")
    List<Book> findAllWithBookstore();
}