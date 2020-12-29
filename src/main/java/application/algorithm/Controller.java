package application.algorithm;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import application.entity.User;
import application.services.OrderService;
import application.services.UserService;

@RestController
@RequestMapping("/rest")
public class Controller {
	private final OrderService orderService;
	private final UserService userSerivce;

	public Controller(OrderService orderService, UserService userSerivce) {
		this.orderService = orderService;
		this.userSerivce = userSerivce;
	}

	public Long calculatePenaltyAmount(String userName) {
		return orderService.findAll().stream().filter(order -> order.getUser().getName().equals(userName))
				.filter(order -> order.getDateOfReturn() == null)
				.filter(order -> Duration.between(order.getDateOfOrder(), LocalDateTime.now()).toDays() >= 3)
				.mapToLong(order -> (Duration.between(order.getDateOfOrder(), LocalDateTime.now()).toDays() - 3) * 2)
				.sum();
	}

	@GetMapping("/")
	public List<Penalty> getPenalties() {
		System.out.println("penalties");
		List<User> users = userSerivce.findAll();
		List<Penalty> penalties = new ArrayList<>();

		users.stream().forEach(user -> {
			penalties.add(new Penalty(user.getName(), calculatePenaltyAmount(user.getName())));
		});
		return penalties;
	}

	@GetMapping("/{userName}")
	public Penalty getPenalty(@PathVariable("userName") String userName) {
		System.out.println(userName);
		Long penalty = calculatePenaltyAmount(userName);
		return new Penalty(userName, penalty);
	}
}