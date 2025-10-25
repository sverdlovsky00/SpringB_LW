package ru.arhipov.url_db_rest_max.entity;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bookstores")
public class Bookstore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String address;
    private String city;
    private String phone;

    @OneToMany(mappedBy = "bookstore", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    //Констр
    public Bookstore() {}

    public Bookstore(String name, String address, String city, String phone) {
        this.name = name;
        this.address = address;
        this.city = city;
        this.phone = phone;}
    //Гет-сет
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}

    public String getAddress() {return address;}
    public void setAddress(String address) {this.address = address;}

    public String getCity() {return city;}
    public void setCity(String city) {this.city = city;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public List<Book> getBooks() {return books;}
    public void setBooks(List<Book> books) {this.books = books;}

    //Вспомогательные
    public void addBook(Book book) {books.add(book);
        book.setBookstore(this);}

    public void removeBook(Book book) {books.remove(book);
        book.setBookstore(null);}
}
