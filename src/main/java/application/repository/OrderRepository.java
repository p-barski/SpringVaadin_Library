package application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import application.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}