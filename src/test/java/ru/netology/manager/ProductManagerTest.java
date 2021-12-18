package ru.netology.manager;

import ru.netology.repository.ProductRepository;

import static org.junit.jupiter.api.Assertions.*;

import ru.netology.domain.NotFoundException;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Product;
import ru.netology.domain.Book;
import ru.netology.domain.Smartphone;
import org.junit.jupiter.api.BeforeEach;

public class ProductManagerTest {

    ProductRepository repository = new ProductRepository();
    ProductManager manager = new ProductManager(repository);
    Product book3 = new Book(3, "dar", 600, "noname");
    Product book2 = new Book(2, "master", 300, "bulgakov");
    Product book1 = new Book(1, "dar", 200, "nabokov");
    Product smartphone2 = new Smartphone(4, "redmi", 6000, "xaomi");
    Product smartphone1 = new Smartphone(5, "lumia", 5000, "nokia");

    @BeforeEach
    public void setUp() {
        manager.add(book1);
        manager.add(book2);
        manager.add(book3);
        manager.add(smartphone1);
        manager.add(smartphone2);
    }

    @Test
    //
    public void shouldSearchByNameBook() {
        Product[] expected = {book2};
        Product[] actual = manager.searchBy("master");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByAuthor() {
        Product[] expected = {book1};
        Product[] actual = manager.searchBy("nabokov");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotFindNameBook() {
        Product[] expected = {};
        Product[] actual = manager.searchBy("book3");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotFindAuthor() {
        Product[] expected = {};
        Product[] actual = manager.searchBy("dostoevsky");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByNameSmartphone() {
        Product[] expected = {smartphone2};
        Product[] actual = manager.searchBy("redmi");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByManufacture() {
        Product[] expected = {smartphone1};
        Product[] actual = manager.searchBy("nokia");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotFindNameSmartphone() {
        Product[] expected = {};
        Product[] actual = manager.searchBy("galaxy");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldNotFindManufacture() {
        Product[] expected = {};
        Product[] actual = manager.searchBy("samsung");
        assertArrayEquals(expected, actual);
    }

    @Test
    public void shouldSearchByNameBookMoreThenOne() {
        Product[] expected = {book1, book3};
        Product[] actual = manager.searchBy("dar");
        assertArrayEquals(expected, actual);
    }

    // проверка удаления существующего элемента
    @Test
    public void shouldRemoveByIdExist() {
        repository.removeById(3);
        Product[] expected = {book1, book2, smartphone1, smartphone2};
        Product[] actual = repository.findAll();
        assertArrayEquals(expected, actual);
    }

    // проверка удаления несуществующего элемента
    @Test
    public void shouldRemoveByIdNotExist() {
        assertThrows(NotFoundException.class, () -> {
            repository.removeById(6);
        });
    }
}
