package bcp;

public interface BookRepository {
	Book getByIsbn(String isbn);
	void updateBook(Book book);
	Book updateBookCachePut(Book book);
	void clearCache(String isbn);
	void delete(String isbn);
}
