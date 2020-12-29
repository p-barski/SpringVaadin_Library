package application.services;

import java.util.List;
import java.util.stream.IntStream;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import application.entity.User;
import application.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public void save(User user) {
		userRepository.save(user);
	}

	public List<User> search(String name, String lastName, String address) {
		return userRepository.search(name, lastName, address);
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	@PostConstruct
	public void addUsers() {
		IntStream.range(0, 10).forEachOrdered(number -> {
			String name = String.format("Name%d", number);
			String lastName = String.format("LastName%d", number);
			String address = String.format("Addres %d", number);
			User user = new User(name, lastName, address);
			userRepository.save(user);
		});
	}
}