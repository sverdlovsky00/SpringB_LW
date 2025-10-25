package ru.arhipov.url_db_rest_max.repository;
import ru.arhipov.url_db_rest_max.entity.Bookstore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookstoreRepository extends JpaRepository<Bookstore, Long> {
    //Найти магаз по названию
    List<Bookstore> findByNameContainingIgnoreCase(String name);
    //Найти магазы по городу
    List<Bookstore> findByCityContainingIgnoreCase(String city);
    //Получить все магазы с количеством книг
    @Query("SELECT bs, COUNT(b) FROM Bookstore bs LEFT JOIN bs.books b GROUP BY bs")
    List<Object[]> findAllWithBookCount();}