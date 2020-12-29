package application.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "_order_")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@NotEmpty
	@ManyToOne(targetEntity = Book.class)
	private Book book;

	@NotNull
	@NotEmpty
	@ManyToOne(targetEntity = User.class)
	private User user;

	@NotNull
	@NotEmpty
	private LocalDateTime dateOfOrder;

	private LocalDateTime dateOfReturn;

	public Order() {
		super();
	}

	public Order(@NotNull @NotEmpty Book book, @NotNull @NotEmpty User user) {
		super();
		this.book = book;
		this.user = user;
		this.dateOfOrder = LocalDateTime.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getDateOfOrder() {
		return dateOfOrder;
	}

	public void setDateOfOrder(LocalDateTime dateOfOrder) {
		this.dateOfOrder = dateOfOrder;
	}

	public LocalDateTime getDateOfReturn() {
		return dateOfReturn;
	}

	public void setDateOfReturn(LocalDateTime dateOfReturn) {
		this.dateOfReturn = dateOfReturn;
	}
}