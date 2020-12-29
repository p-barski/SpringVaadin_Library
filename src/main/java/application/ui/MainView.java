package application.ui;

import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import application.entity.Book;
import application.entity.Order;
import application.entity.User;
import application.services.BookService;
import application.services.OrderService;
import application.services.UserService;

@Route("")
public class MainView extends VerticalLayout {
	private final Integer MIN_NAME_LENGTH = 2;

	private final BookService bookService;
	private final OrderService orderService;
	private final UserService userService;

	private final Grid<Book> bookGrid = new Grid<>(Book.class);
	private final TextField nameField = new TextField("Name");
	private final TextField lastNameField = new TextField("Last Name");
	private final TextField addressField = new TextField("Adress");
	private final Div messageDiv = new Div();

	public MainView(BookService bookService, OrderService orderService, UserService userService) {
		this.bookService = bookService;
		this.orderService = orderService;
		this.userService = userService;
		configureUI();
	}

	private void configureUI() {
		this.setSizeFull();

		nameField.setMaxLength(53);
		lastNameField.setMaxLength(53);
		addressField.setMaxLength(100);

		bookGrid.setSizeFull();
		bookGrid.setColumns("title", "author");
		bookGrid.setItems(bookService.findAvailableBooks());

		this.add(new H2("Books"), createButtonsAndTextFields(), bookGrid);
	}

	private Component createButtonsAndTextFields() {
		Button orderButton = new Button("Order selected book");

		orderButton.setThemeName("primary");
		orderButton.addClickListener(click -> orderBook());

		HorizontalLayout textFieldsLayout = new HorizontalLayout(nameField, lastNameField, addressField);
		HorizontalLayout buttonsLayout = new HorizontalLayout(orderButton);
		Div wrapperLayout = new Div(textFieldsLayout, buttonsLayout, messageDiv);
		textFieldsLayout.setDefaultVerticalComponentAlignment(Alignment.BASELINE);
		wrapperLayout.setWidth("100%");

		return wrapperLayout;
	}

	private void clearTextFields() {
		nameField.clear();
		lastNameField.clear();
		addressField.clear();
		messageDiv.setText("");
	}

	private void orderBook() {
		Set<Book> selectedBooks = bookGrid.getSelectionModel().getSelectedItems();
		if (selectedBooks.isEmpty()) {
			messageDiv.setText("Nothing selected.");
			return;
		}
		Book selectedBook = selectedBooks.stream().findFirst().get();

		String name = nameField.getValue();
		String lastName = lastNameField.getValue();
		String address = addressField.getValue();

		if (!validateClientData(name, lastName, address))
			return;

		List<User> foundUser = userService.search(name, lastName, address);
		User user;
		System.out.println("id " + selectedBook.getId().toString());
		if (foundUser.isEmpty()) {
			user = new User(name, lastName, address);
			System.out.println("saving user");
			userService.save(user);
		} else {
			user = foundUser.get(0);
		}
		selectedBook.setRented(true);
		bookService.save(selectedBook);
		System.out.println("Creating new order");
		Order order = new Order(selectedBook, user);
		System.out.println("Saving order");
		orderService.save(order);

		clearTextFields();
		bookGrid.setItems(bookService.findAvailableBooks());
		messageDiv.setText("Ordered book");
	}

	private Boolean validateClientData(String name, String lastName, String address) {
		// Validating name
		String nameRegex = String.format("[\\p{L} ]{%d,}", MIN_NAME_LENGTH);
		Pattern pattern = Pattern.compile(nameRegex);
		Matcher matcher = pattern.matcher(name);
		if (!matcher.matches()) {
			messageDiv.setText(String.format("Name has to contain letters only and be at least %d letters long.",
					MIN_NAME_LENGTH));
			return false;
		}

		// Validating last name
		pattern = Pattern.compile(nameRegex);
		matcher = pattern.matcher(lastName);
		if (!matcher.matches()) {
			messageDiv.setText(String.format("Last name has to contain letters only and be at least %d letters long.",
					MIN_NAME_LENGTH));
			return false;
		}

		// Validating address
		// Address examples:
		// Adress 1
		// Adress 2a
		// Adress 3a/1
		// Adress 4b/2b
		// Adress 5/3c
		// Adress 6/4

		String addressRegex = "^([\\p{L} ]+ )(\\d+|\\d+/\\d+|\\d+\\p{L}{1}/\\d+|\\d+\\p{L}{1}/\\d+\\p{L}{1}|\\d+/\\d+\\p{L}{1}|\\d+\\p{L}{1})$";
		pattern = Pattern.compile(addressRegex);
		matcher = pattern.matcher(address);
		if (!matcher.matches()) {
			messageDiv.setText("Incorrect address.");
			return false;
		}
		return true;
	}
}