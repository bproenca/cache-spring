package bcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CachingApplication implements CommandLineRunner {

	private static final Logger logger = LoggerFactory.getLogger(CachingApplication.class);

	@Autowired
	private BookRepository bookRepository;

	public static void main(String[] args) {
		SpringApplication.run(CachingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info(".... Fetching books");
		logger.info("isbn-1 -->{} ", bookRepository.getByIsbn("isbn-1"));
		logger.info("isbn-2 -->{} ", bookRepository.getByIsbn("isbn-2"));
		logger.info("isbn-1 -->{} ", bookRepository.getByIsbn("isbn-1"));
		logger.info("isbn-2 -->{} ", bookRepository.getByIsbn("isbn-2"));

		logger.info("------------------------");
		logger.info("isbn-3 -->{} ", bookRepository.getByIsbn("isbn-3"));
		logger.info("Update book 3 >> 33 (don't refresh cache)");
		bookRepository.updateBook(new Book("isbn-3", "33"));
		logger.info("isbn-3 -->{} ", bookRepository.getByIsbn("isbn-3"));
		logger.info("isbn-3 -->{} ", bookRepository.getByIsbn("isbn-3"));
		logger.info("------------------------");
		logger.info("Update/CachePut book 3 >> 333");
		bookRepository.updateBookCachePut(new Book("isbn-3", "333"));
		logger.info("isbn-3 -->{} ", bookRepository.getByIsbn("isbn-3"));
		logger.info("------------------------");
		logger.info("ClearCache book 3");
		bookRepository.clearCache("isbn-3");
		logger.info("isbn-3 -->{} ", bookRepository.getByIsbn("isbn-3"));
		logger.info("isbn-3 -->{} ", bookRepository.getByIsbn("isbn-3"));
		logger.info("------------------------");
		logger.info("Delete book 3");
		bookRepository.delete("isbn-3");
		logger.info("isbn-3 -->{} ", bookRepository.getByIsbn("isbn-3"));
	}

}
