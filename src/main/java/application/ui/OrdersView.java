package application.ui;

import java.time.LocalDateTime;
import java.util.Set;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import application.entity.Book;
import application.entity.Order;
import application.services.BookService;
import application.services.OrderService;

@Route("orders")
public class OrdersView extends VerticalLayout {
	private final OrderService orderService;
	private final BookService bookService;
	private final Grid<Order> orderGrid = new Grid<>(Order.class);

	public OrdersView(OrderService orderService, BookService bookService) {
		this.orderService = orderService;
		this.bookService = bookService;
		configureUI();
	}

	private void configureUI() {
		Button refreshButton = new Button("Refresh");
		Button returnBookButton = new Button("Mark as returned");

		refreshButton.setThemeName("primary");
		refreshButton.addClickListener(click -> orderGrid.setItems(orderService.findAll()));
		returnBookButton.setThemeName("primary");
		returnBookButton.addClickListener(click -> returnBook());
		this.setSizeFull();

		orderGrid.setSizeFull();
		orderGrid.setColumns("user", "book", "dateOfOrder", "dateOfReturn");
		orderGrid.setItems(orderService.findAll());

		this.add(new H2("Orders"), refreshButton, returnBookButton, orderGrid);
	}

	private void returnBook() {
		Set<Order> selectedOrders = orderGrid.getSelectionModel().getSelectedItems();
		if (selectedOrders.isEmpty())
			return;
		Order selectedOrder = selectedOrders.stream().findFirst().get();
		Book rentedBook = selectedOrder.getBook();
		if (selectedOrder.getDateOfReturn() != null)
			return;

		rentedBook.setRented(false);
		selectedOrder.setDateOfReturn(LocalDateTime.now());

		bookService.save(rentedBook);
		orderService.save(selectedOrder);

		orderGrid.setItems(orderService.findAll());
	}
}