package application.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import application.entity.Book;
import application.entity.Order;
import application.entity.User;
import application.repository.OrderRepository;

@Service
public class OrderService {
	private OrderRepository orderRepository;
	private UserService userService;
	private BookService bookService;

	public OrderService(OrderRepository orderRepository, UserService userService, BookService bookService) {
		this.orderRepository = orderRepository;
		this.userService = userService;
		this.bookService = bookService;
	}

	public void save(Order order) {
		if (order == null)
			return;
		orderRepository.save(order);
	}

	public List<Order> findAll() {
		return orderRepository.findAll();
	}

	public void deleteAll() {
		orderRepository.deleteAll();
	}

	@PostConstruct
	public void addOrders() {
		List<Book> books = bookService.findAvailableBooks();
		List<User> users = userService.findAll();
		IntStream.range(0, 7).forEachOrdered(number -> {
			Book book = books.get(number);
			User user = users.get((int) (number / 2));

			book.setRented(true);
			bookService.save(book);
			Order order = new Order(book, user);
			order.setDateOfOrder(LocalDateTime.now().minusDays(4 + number));
			orderRepository.save(order);
		});
	}
}