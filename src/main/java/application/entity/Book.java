package application.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Book {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@NotNull
	@NotEmpty
	private String title;

	@NotNull
	@NotEmpty
	private String author;

	@NotNull
	@NotEmpty
	private Boolean rented;

	@OneToMany(targetEntity = Order.class, fetch = FetchType.EAGER)
	private List<Order> orders;

	public Book() {
		super();
		this.rented = false;
	}

	public Book(@NotNull @NotEmpty String title, @NotNull @NotEmpty String author) {
		super();
		this.title = title;
		this.author = author;
		this.rented = false;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public Boolean isRented() {
		return this.rented;
	}

	public void setRented(Boolean rented) {
		this.rented = rented;
	}

	@Override
	public String toString() {
		return this.title + " | " + this.author + " | " + this.rented.toString();
	}
}