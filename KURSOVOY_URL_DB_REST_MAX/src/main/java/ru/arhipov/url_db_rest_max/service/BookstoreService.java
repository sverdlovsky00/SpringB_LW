package ru.arhipov.url_db_rest_max.service;
import ru.arhipov.url_db_rest_max.entity.Bookstore;
import ru.arhipov.url_db_rest_max.repository.BookstoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookstoreService {

    private final BookstoreRepository bookstoreRepository;

    public BookstoreService(BookstoreRepository bookstoreRepository) {
        this.bookstoreRepository = bookstoreRepository;}

    public List<Bookstore> findAll(){return bookstoreRepository.findAll();}

    public Optional<Bookstore> findById(Long id){return bookstoreRepository.findById(id);}

    public Bookstore save(Bookstore bookstore){return bookstoreRepository.save(bookstore);}

    public void deleteById(Long id){bookstoreRepository.deleteById(id);}

    public List<Bookstore> findByName(String name){return bookstoreRepository
            .findByNameContainingIgnoreCase(name);}

    public List<Bookstore> findByCity(String city){return bookstoreRepository
            .findByCityContainingIgnoreCase(city);}

    public long count(){return bookstoreRepository.count();}
    //Статистика по магазам
    public List<Object[]> getBookstoreStatistics(){
        return bookstoreRepository.findAllWithBookCount();}
    //Расчет средней цены в магазах
    public Double calculateAverageBookPriceInStore(Long bookstoreId){
        return bookstoreRepository.findById(bookstoreId)
                .map(bookstore -> bookstore.getBooks().stream()
                        .mapToDouble(book -> book.getPrice().doubleValue())
                        .average()
                        .orElse(0.0))
                .orElse(0.0);}
}
