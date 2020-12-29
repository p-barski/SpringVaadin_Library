package application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import application.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("SELECT k FROM User k WHERE lower(k.name)=lower(:name) AND lower(k.lastName)=lower(:lastName) AND lower(k.address)=lower(:address)")
	List<User> search(@Param("name") String name, @Param("lastName") String lastName, @Param("address") String address);
}