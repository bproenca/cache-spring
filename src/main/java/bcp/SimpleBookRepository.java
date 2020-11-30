package bcp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class SimpleBookRepository implements BookRepository {

	private Map<String, Book> database = new HashMap<String, Book>();

	public SimpleBookRepository() {
		database.put("isbn-1", new Book("isbn-1", "Book 1"));
		database.put("isbn-2", new Book("isbn-2", "Book 2"));
		database.put("isbn-3", new Book("isbn-3", "Book 3"));
	}


	@Override
	@Cacheable("books")
	public Book getByIsbn(String isbn) {
		simulateSlowService();
		return database.get(isbn);
	}

	// Don't do this at home
	private void simulateSlowService() {
		try {
			long time = 3000L;
			Thread.sleep(time);
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		}
	}

	@Override
	public void updateBook(Book book) {
		database.put(book.getIsbn(), book);
	}

	@Override
	@CachePut(value="books", key = "#book.isbn")
	public Book updateBookCachePut(Book book) {
		database.put(book.getIsbn(), book);
		return book;
	}

	@Override
	@CacheEvict(value="books")
	public void clearCache(String isbn) {
		
	}
	
	@Override
	@CacheEvict(value="books")
	public void delete(String isbn) {
		database.remove(isbn);
	}


}
