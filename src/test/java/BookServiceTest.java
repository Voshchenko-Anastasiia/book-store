import com.epam.rd.autocode.spring.project.dto.BookDTO;
import com.epam.rd.autocode.spring.project.model.Book;
import com.epam.rd.autocode.spring.project.model.enums.AgeGroup;
import com.epam.rd.autocode.spring.project.model.enums.Language;
import com.epam.rd.autocode.spring.project.repo.BookRepository;
import com.epam.rd.autocode.spring.project.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;
    private BookDTO testBookDto;

    @BeforeEach
    void setUp() {
        testBook = new Book();
        testBook.setId(1L);
        testBook.setName("Spring in Action");
        testBook.setPrice(BigDecimal.valueOf(49.99));
        testBook.setGenre("Tech");

        testBookDto = new BookDTO(
                1L, "Spring in Action", "Tech", AgeGroup.ADULT,
                BigDecimal.valueOf(49.99), null, "Craig Walls",
                500, "Hardcover", "Great book", Language.ENGLISH
        );
    }

    @Test
    void getAllBooks_ShouldReturnDtoList() {
        when(bookRepository.findAll()).thenReturn(Collections.singletonList(testBook));

        List<BookDTO> result = bookService.getAllBooks();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Spring in Action", result.get(0).getName());
    }

    @Test
    void getAllBooksPaginated_ShouldReturnPageOfDtos() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(testBook));
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        Page<BookDTO> result = bookService.getAllBooksPaginated(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Spring in Action", result.getContent().get(0).getName());
    }

    @Test
    void searchBooksByNamePaginated_ShouldReturnFilteredPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(testBook));
        when(bookRepository.findByNameContainingIgnoreCase("Spring", pageable)).thenReturn(bookPage);

        Page<BookDTO> result = bookService.searchBooksByNamePaginated("Spring", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void deleteBookByName_WhenBookExists_ShouldDelete() {
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(testBook));
        when(bookRepository.findByNameContainingIgnoreCase("Spring", Pageable.unpaged())).thenReturn(bookPage);

        assertDoesNotThrow(() -> bookService.deleteBookByName("Spring"));
        verify(bookRepository, times(1)).delete(testBook);
    }

    @Test
    void deleteBookByName_WhenBookDoesNotExist_ShouldThrowException() {
        when(bookRepository.findByNameContainingIgnoreCase("Unknown", Pageable.unpaged())).thenReturn(Page.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> bookService.deleteBookByName("Unknown"));
        assertTrue(exception.getMessage().contains("Cannot delete"));
    }

    @Test
    void getBookByName_WhenBookExists_ShouldReturnDto() {
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(testBook));
        when(bookRepository.findByNameContainingIgnoreCase("Spring", Pageable.unpaged())).thenReturn(bookPage);

        BookDTO result = bookService.getBookByName("Spring");

        assertNotNull(result);
        assertEquals("Spring in Action", result.getName());
    }

    @Test
    void getBookByName_WhenBookDoesNotExist_ShouldThrowException() {
        when(bookRepository.findByNameContainingIgnoreCase("Unknown", Pageable.unpaged())).thenReturn(Page.empty());

        assertThrows(RuntimeException.class, () -> bookService.getBookByName("Unknown"));
    }

    @Test
    void updateBookByName_WhenBookExists_ShouldSaveAndReturnUpdatedDto() {
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(testBook));
        when(bookRepository.findByNameContainingIgnoreCase("Spring", Pageable.unpaged())).thenReturn(bookPage);
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        BookDTO result = bookService.updateBookByName("Spring", testBookDto);

        assertNotNull(result);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBookByName_WhenBookDoesNotExist_ShouldThrowException() {
        when(bookRepository.findByNameContainingIgnoreCase("Unknown", Pageable.unpaged())).thenReturn(Page.empty());

        assertThrows(RuntimeException.class, () -> bookService.updateBookByName("Unknown", testBookDto));
    }

    @Test
    void addBook_ShouldSaveAndReturnDto() {
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        BookDTO result = bookService.addBook(testBookDto);

        assertNotNull(result);
        assertEquals("Spring in Action", result.getName());
    }

    @Test
    void findAll_ShouldReturnRawPage() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> bookPage = new PageImpl<>(Collections.singletonList(testBook));
        when(bookRepository.findAll(pageable)).thenReturn(bookPage);

        Page<Book> result = bookService.findAll(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    void findById_WhenFound_ShouldReturnBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Book result = bookService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void findById_WhenNotFound_ShouldReturnNull() {
        when(bookRepository.findById(2L)).thenReturn(Optional.empty());

        Book result = bookService.findById(2L);

        assertNull(result);
    }

    @Test
    void save_ShouldMapAndSave() {
        bookService.save(testBookDto);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void deleteById_ShouldInvokeRepositoryDelete() {
        bookService.deleteById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }
}