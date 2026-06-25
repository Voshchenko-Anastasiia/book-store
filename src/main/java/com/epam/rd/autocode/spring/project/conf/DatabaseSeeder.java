package com.epam.rd.autocode.spring.project.conf;

import com.epam.rd.autocode.spring.project.dto.UserRegistrationDTO;
import com.epam.rd.autocode.spring.project.exception.UserNotFoundException;
import com.epam.rd.autocode.spring.project.model.*;
import com.epam.rd.autocode.spring.project.model.enums.*;
import com.epam.rd.autocode.spring.project.repo.*;
import com.epam.rd.autocode.spring.project.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Configuration
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner seedDatabase(UserService userService,
                                          BookRepository bookRepository,
                                          OrderRepository orderRepository) {
        return args -> {

            // --- 1. SEED USERS ---
            createUserIfNotExists(userService, "admin@bookstore.com", "Administrator", "Admin_2026!", "ADMIN");
            createUserIfNotExists(userService, "client1@gmail.com", "Alexander Wright", "password123", "CLIENT");
            createUserIfNotExists(userService, "client2@gmail.com", "Emma Watson", "password123", "CLIENT");

            // --- 2. SEED BOOKS ---
            if (bookRepository.count() == 0) {
                List<Book> defaultBooks = List.of(
                        // Science & Philosophy
                        buildBook("The Selfish Gene", "Richard Dawkins", "Science", "ADULT", 19.99, "1976-01-01", 360, "ENGLISH", "A foundational work on evolutionary biology.", "Hardcover"),
                        buildBook("The God Delusion", "Richard Dawkins", "Philosophy", "ADULT", 18.50, "2006-01-01", 463, "ENGLISH", "A philosophical critique of religion.", "Paperback"),
                        buildBook("The Blind Watchmaker", "Richard Dawkins", "Science", "ADULT", 16.00, "1986-01-01", 311, "ENGLISH", "An explanation of natural selection.", "Paperback"),
                        buildBook("A Brief History of Time", "Stephen Hawking", "Science", "ADULT", 14.99, "1988-01-01", 256, "ENGLISH", "Cosmology for general readers.", "Hardcover"),
                        buildBook("The Universe in a Nutshell", "Stephen Hawking", "Science", "ADULT", 22.00, "2001-01-01", 216, "ENGLISH", "Theoretical physics developments illustrated.", "Hardcover"),
                        buildBook("Brief Answers to the Big Questions", "Stephen Hawking", "Science", "ADULT", 17.95, "2018-01-01", 256, "ENGLISH", "Final thoughts on universes biggest challenges.", "Paperback"),
                        // Biographies
                        buildBook("Slash: The Autobiography", "Slash", "Biography", "ADULT", 21.00, "2007-01-01", 480, "ENGLISH", "Life of the iconic Guns N Roses guitarist.", "Paperback"),
                        buildBook("Lives of Brian", "Brian Johnson", "Biography", "ADULT", 24.99, "2022-01-01", 384, "ENGLISH", "Memoir of AC/DC front man.", "Hardcover"),
                        buildBook("Rainbow in the Dark", "Ronnie James Dio", "Biography", "ADULT", 20.00, "2021-01-01", 320, "ENGLISH", "Heavy metal icons life story.", "Hardcover"),
                        buildBook("I Am Ozzy", "Ozzy Osbourne", "Biography", "ADULT", 18.99, "2009-01-01", 416, "ENGLISH", "Unfiltered memoir of the Prince of Darkness.", "Paperback"),
                        buildBook("Brian May: The Definitive Biography", "Laura Jackson", "Biography", "ADULT", 35.00, "2017-01-01", 256, "ENGLISH", "History of Queen captured in stereoscopic photos.", "Hardcover with stereoscope included"),
                        buildBook("Let Love Rule", "Lenny Kravitz", "Biography", "ADULT", 22.50, "2020-01-01", 272, "ENGLISH", "Reflections on the first 25 years of life.", "Hardcover"),
                        buildBook("Me", "Elton John", "Biography", "ADULT", 19.99, "2019-01-01", 384, "ENGLISH", "The official, dramatic autobiography.", "Paperback"),
                        buildBook("Blues All Around Me", "B.B. King", "Biography", "ADULT", 16.95, "1996-01-01", 368, "ENGLISH", "The life and legacy of a blues icon.", "Paperback"),
                        // Ukrainian Poetry
                        buildBook("Kobzar", "Taras Shevchenko", "Poetry", "ADULT", 15.00, "1840-01-01", 400, "UKRAINIAN", "The core literary masterpiece of Ukrainian verse.", "Premium Gift Edition"),
                        buildBook("Anthology of Ukrainian Poetry", "Ivan Malkovych", "Poetry", "ADULT", 25.00, "2016-01-01", 600, "UKRAINIAN", "Massive collection of classical Ukrainian verse.", "Fabric hardcover"),
                        buildBook("Selected Poems", "Lina Kostenko", "Poetry", "ADULT", 12.50, "2012-01-01", 320, "UKRAINIAN", "Deep and resonant modern poetry.", "Hardcover"),
                        buildBook("Green Gospel", "Bohdan-Ihor Antonych", "Poetry", "ADULT", 11.00, "2009-01-01", 192, "UKRAINIAN", "Lyrical pagan and nature themes from Western Ukraine.", "Pocket format"),
                        buildBook("Letters from Ukraine", "Vasyl Stus", "Poetry", "ADULT", 13.40, "2015-01-01", 240, "UKRAINIAN", "Dissident prison poetry and personal notes.", "Hardcover"),
                        buildBook("Selected Verse", "Vasyl Symonenko", "Poetry", "TEEN", 10.50, "2010-01-01", 180, "UKRAINIAN", "Emotional, sincere patriotic and romantic text.", "Paperback"),
                        buildBook("The Book of Grace", "Attila Mohylny", "Poetry", "ADULT", 14.00, "1993-01-01", 150, "UKRAINIAN", "Rare contemporary Kyiv underground poetry.", "Paperback"),
                        buildBook("Poems of the Sun", "Ivan Drach", "Poetry", "ADULT", 12.00, "1962-01-01", 210, "UKRAINIAN", "Avant-garde Soviet-era Ukrainian poetry.", "Hardcover"),
                        buildBook("Selected Lyrics", "Borys Oliynyk", "Poetry", "ADULT", 11.50, "1975-01-01", 230, "UKRAINIAN", "Traditional values and classical style stanzas.", "Hardcover"),
                        // Java Specific Technical Books
                        buildBook("Effective Java (3rd Edition)", "Joshua Bloch", "Java", "ADULT", 45.00, "2017-12-01", 412, "ENGLISH", "Best practices guide for explicit language features.", "Technical Guide"),
                        buildBook("Java: The Complete Reference", "Herbert Schildt", "Java", "ADULT", 55.00, "2021-11-01", 1248, "ENGLISH", "Monolithic comprehensive map of the full runtime language.", "Reference Manual"),
                        buildBook("Head First Java", "Kathy Sierra", "Java", "TEEN", 42.00, "2022-05-01", 720, "ENGLISH", "Highly interactive brain-friendly visual coding map.", "Softcover Workbook"),
                        buildBook("Java Concurrency in Practice", "Brian Goetz", "Java", "ADULT", 48.50, "2006-05-01", 384, "ENGLISH", "The holy grail reference for multi-threaded systems architecture.", "Paperback Core Tech"),
                        buildBook("Core Java Volume I", "Cay S. Horstmann", "Java", "ADULT", 50.00, "2022-03-01", 840, "ENGLISH", "Deep fundamental guide on libraries and internal logic.", "Hardcover Reference"),
                        buildBook("Thinking in Java", "Bruce Eckel", "Java", "ADULT", 38.00, "2006-02-01", 1480, "ENGLISH", "Object oriented principles unpacked directly.", "Paperback"),
                        // Uncle Bob Clean Coding Series
                        buildBook("Clean Code", "Robert C. Martin", "Development", "ADULT", 39.99, "2008-08-01", 464, "ENGLISH", "Craftsmanship manual for clear, maintainable architecture parsing.", "Classic Paperback"),
                        buildBook("The Clean Coder", "Robert C. Martin", "Development", "ADULT", 34.99, "2011-05-01", 242, "ENGLISH", "A code of conduct and professional guide for software engineers.", "Softcover"),
                        buildBook("Clean Architecture", "Robert C. Martin", "Development", "ADULT", 42.00, "2017-09-01", 432, "ENGLISH", "Universal design principles for system abstraction layers.", "Hardcover Manual"),
                        buildBook("Clean Agile", "Robert C. Martin", "Development", "ADULT", 29.95, "2019-10-01", 240, "ENGLISH", "Back to principles explanation of genuine agile practices.", "Paperback"),
                        buildBook("Clean Craftsmanship", "Robert C. Martin", "Development", "ADULT", 38.00, "2021-09-01", 352, "ENGLISH", "The core disciplines and practices of software developers.", "Hardcover"),
                        buildBook("Agile Software Development", "Robert C. Martin", "Development", "ADULT", 65.00, "2002-10-01", 529, "ENGLISH", "Comprehensive patterns and case studies walkthrough.", "Hardcover Reference"),
                        // General Development, Business, Fiction & History
                        buildBook("The Pragmatic Programmer", "Andrew Hunt", "Development", "ADULT", 44.95, "2019-09-01", 352, "ENGLISH", "Your journey to mastery guidelines.", "Hardcover"),
                        buildBook("Design Patterns", "Erich Gamma", "Development", "ADULT", 52.00, "1994-10-01", 395, "ENGLISH", "Elements of reusable object-oriented software patterns.", "Hardcover Classic"),
                        buildBook("Introduction to Algorithms", "Thomas H. Cormen", "Science", "ADULT", 85.00, "2022-04-01", 1312, "ENGLISH", "Detailed analytical breakdown of complex data workflows.", "Heavy Text Book"),
                        buildBook("Refactoring", "Martin Fowler", "Development", "ADULT", 47.99, "2018-11-01", 448, "ENGLISH", "Improving design of existing source logic structures cleanly.", "Hardcover"),
                        buildBook("The Lean Startup", "Eric Ries", "Business", "ADULT", 24.99, "2011-09-01", 336, "ENGLISH", "Constant innovation for business structures.", "Paperback"),
                        buildBook("Zero to One", "Peter Thiel", "Business", "ADULT", 21.00, "2014-09-01", 224, "ENGLISH", "Notes on startups, or how to build the future.", "Hardcover"),
                        buildBook("Good to Great", "Jim Collins", "Business", "ADULT", 26.50, "2001-10-01", 400, "ENGLISH", "Why some companies make the leap and others do not.", "Hardcover"),
                        buildBook("Steve Jobs", "Walter Isaacson", "Biography", "ADULT", 20.00, "2011-10-01", 656, "ENGLISH", "Exclusive biography of the Apple co-founder.", "Paperback"),
                        buildBook("1984", "George Orwell", "Fiction", "TEEN", 10.99, "1949-06-01", 328, "ENGLISH", "Dystopian nightmare tracking total societal monitoring.", "Paperback"),
                        buildBook("Animal Farm", "George Orwell", "Fiction", "TEEN", 8.99, "1945-08-01", 112, "ENGLISH", "Satirical allegorical fable on authority manipulation.", "Pocket edition"),
                        buildBook("Brave New World", "Aldous Huxley", "Fiction", "ADULT", 12.50, "1932-01-01", 288, "ENGLISH", "Utopian bio-engineered tracking of human class creation.", "Paperback"),
                        buildBook("Fahrenheit 451", "Ray Bradbury", "Fiction", "TEEN", 11.99, "1953-10-01", 256, "ENGLISH", "A society where books are outlawed and burned.", "Paperback"),
                        buildBook("The Hobbit", "J.R.R. Tolkien", "Fiction", "CHILD", 14.95, "1937-09-01", 310, "ENGLISH", "There and back again epic fantasy precursor.", "Illustrated Hardcover"),
                        buildBook("The Lord of the Rings", "J.R.R. Tolkien", "Fiction", "TEEN", 30.00, "1954-07-01", 1216, "ENGLISH", "The single unified master edition trilogy text.", "Paperback Compilation"),
                        buildBook("Dune", "Frank Herbert", "Fiction", "TEEN", 15.99, "1965-06-01", 604, "ENGLISH", "Interstellar planetary desert political mastery saga.", "Paperback"),
                        buildBook("Neuromancer", "William Gibson", "Fiction", "ADULT", 9.99, "1984-07-01", 271, "ENGLISH", "The foundational masterpiece archetype of cyberpunk.", "Mass Market Edition"),
                        buildBook("Snow Crash", "Neal Stephenson", "Fiction", "ADULT", 13.50, "1992-06-01", 470, "ENGLISH", "Virtual metaverse and linguistic virus tracing elements.", "Paperback"),
                        buildBook("Sapiens", "Yuval Noah Harari", "History", "ADULT", 22.00, "2011-01-01", 443, "ENGLISH", "A brief history of humankind.", "Paperback"),
                        buildBook("Homo Deus", "Yuval Noah Harari", "History", "ADULT", 23.50, "2015-01-01", 450, "ENGLISH", "A brief history of tomorrow.", "Hardcover"),
                        buildBook("21 Lessons for the 21st Century", "Yuval Noah Harari", "History", "ADULT", 21.00, "2018-08-01", 368, "ENGLISH", "Navigating modern immediate existential questions.", "Paperback"),
                        buildBook("The Silk Roads", "Peter Frankopan", "History", "ADULT", 16.99, "2015-08-01", 656, "ENGLISH", "A major new history of the world center axes.", "Paperback")
                );
                bookRepository.saveAll(defaultBooks);
            }

            // --- 3. SEED ORDERS ---
            if (orderRepository.count() == 0) {
                createSampleOrders(orderRepository, userService, bookRepository);
            }
        };
    }

    private void createSampleOrders(OrderRepository orderRepository, UserService userService, BookRepository bookRepository) {
        List<Book> allBooks = bookRepository.findAll();
        String[] clientEmails = {"client1@gmail.com", "client2@gmail.com"};
        Random random = new Random();
        OrderStatus[] statuses = OrderStatus.values();

        for (int i = 0; i < 5; i++) { // Generate 5 random orders
            String email = clientEmails[random.nextInt(clientEmails.length)];
            User user = userService.getUserByEmail(email);
            Order order = new Order();
            order.setUser(user);
            order.setStatus(statuses[random.nextInt(statuses.length)]);
            order.setOrderDate(LocalDateTime.now().minusDays(random.nextInt(10)));

            List<OrderItem> items = new ArrayList<>();
            BigDecimal total = BigDecimal.ZERO;

            int booksInOrder = random.nextInt(2) + 1;
            for (int j = 0; j < booksInOrder; j++) {
                Book book = allBooks.get(random.nextInt(allBooks.size()));
                OrderItem item = new OrderItem();
                item.setBook(book);
                item.setOrder(order);
                item.setQuantity(random.nextInt(2) + 1);
                item.setPrice(book.getPrice());

                items.add(item);
                total = total.add(book.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            }

            order.setItems(items);
            order.setTotalPrice(total);
                orderRepository.save(order);
            }
        System.out.println("Orders seeded successfully.");
    }

    private void createUserIfNotExists(UserService userService, String email, String name, String password, String role) {
        try {
            userService.getUserByEmail(email);
        } catch (UserNotFoundException e) {
            UserRegistrationDTO dto = new UserRegistrationDTO();
            dto.setEmail(email);
            dto.setName(name);
            dto.setPassword(password);
            dto.setRole(role);
            userService.registerNewUser(dto);
        }
    }

    private Book buildBook(String name, String author, String genre, String ageGroup,
                           double price, String publicationYear, int numberOfPages,
                           String language, String description, String characteristics) {
        Book book = new Book();
        book.setName(name);
        book.setAuthor(author);
        book.setGenre(genre);
        book.setAgeGroup(AgeGroup.valueOf(ageGroup.toUpperCase()));
        book.setLanguage(Language.valueOf(language.toUpperCase()));
        book.setPrice(BigDecimal.valueOf(price));
        book.setPublicationDate(LocalDate.parse(publicationYear));
        book.setPages(numberOfPages);
        book.setDescription(description);
        book.setCharacteristics(characteristics);
        return book;
    }
}